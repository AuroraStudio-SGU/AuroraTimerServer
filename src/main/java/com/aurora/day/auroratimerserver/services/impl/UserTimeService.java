package com.aurora.day.auroratimerserver.services.impl;

import com.aurora.day.auroratimerserver.mapper.UserOnlineTimeMapper;
import com.aurora.day.auroratimerserver.model.ReduceTime;
import com.aurora.day.auroratimerserver.model.Term;
import com.aurora.day.auroratimerserver.model.TermUserTime;
import com.aurora.day.auroratimerserver.model.UserOnlineTime;
import com.aurora.day.auroratimerserver.model.UserTimeBuffer;
import com.aurora.day.auroratimerserver.model.vo.UserTimeReport;
import com.aurora.day.auroratimerserver.services.IReduceTimeService;
import com.aurora.day.auroratimerserver.services.ITermService;
import com.aurora.day.auroratimerserver.services.IUserTimeService;
import com.aurora.day.auroratimerserver.utils.WeekUtil;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.row.Db;
import org.noear.solon.annotation.Component;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.annotation.Cache;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserTimeService implements IUserTimeService {

    @Inject
    UserOnlineTimeMapper timeMapper;
    @Inject
    ITermService termService;
    @Inject
    IReduceTimeService reduceTimeService;

    @Override
    public UserTimeBuffer getTodayUserTimeAsBuffer(String uid) {
        UserOnlineTime time = getTodayUserTime(uid);
        if (time == null) {
            time = new UserOnlineTime(uid, 0);
            //异步插入到数据库中
            final UserOnlineTime fixed = time;
            Thread.ofVirtual().start(() -> timeMapper.insert(fixed)).start();
        }
        return new UserTimeBuffer(getUserWeeklyTime(uid), time);
    }

    @Override
    public UserOnlineTime getTodayUserTime(String uid) {
        QueryWrapper wp = new QueryWrapper();
        wp.eq(UserOnlineTime::getUserId, uid)
                .eq(UserOnlineTime::getRecordDate, LocalDate.now());
        return timeMapper.selectOneByQuery(wp);
    }

    @Override
    public void batchUpdate(List<UserTimeBuffer> userTimes) {
        List<UserOnlineTime> list = userTimes.stream().map(UserTimeBuffer::getOnlineTime).toList();
        Db.updateEntitiesBatch(list);
    }

    @Override
    public long getUserWeeklyTime(String uid) {
        String[] week = WeekUtil.getCurrentWeekStarAndEnd();
        UserTimeReport report = timeMapper.queryUserTimeReport(week[0], week[1], uid);
        return report.getWeekTime();
    }

    @Override
    public List<UserTimeReport> queryTimeRank(int weekOffset) {
        String[] week = WeekUtil.getWeekStarAndEnd(weekOffset);
        //先查询本周的统计
        List<UserTimeReport> reports = timeMapper.queryWeekRankTime(week[0], week[1]);
        //查询学期的统计(使用缓存)
        Term term = termService.getTermByWeekStart(week[0]);
        final Map<String, Long> termReport = getTermUserTimeMap(term);
        //查询该周的减时情况
        final Map<String, BigDecimal> reduceReport =
                reduceTimeService.queryReduceTimeListByWeekId(WeekUtil.getWeekIdentifierByOffset(weekOffset))
                        .stream().collect(Collectors.toMap(ReduceTime::getTargetId, ReduceTime::getReduceTime));
        return reports.stream().peek(r -> {
            //添加学期统计
            r.setTotalTime(termReport.getOrDefault(r.getId(), 0L));
            //添加减时统计
            r.setReducedTime(reduceReport.getOrDefault(r.getId(), BigDecimal.ZERO));
        }).toList();
    }

    @Cache(key = "${term.id}", tags = "terms", seconds = 24 * 3600)
    @Override
    public Map<String, Long> getTermUserTimeMap(Term term) {
        String termStart = term.getStart().format(DateTimeFormatter.ISO_DATE);
        String termEnd = term.getEnd().format(DateTimeFormatter.ISO_DATE);
        List<TermUserTime> list = timeMapper.queryTermRankTime(termStart, termEnd);
        return list.stream().collect(Collectors.toMap(TermUserTime::getUid, TermUserTime::getTotalTime));
    }

    @Cache(key = "${offset}", tags = "rank", seconds = 24 * 3600)
    @Override
    public List<UserTimeReport> getLimitUserTimeRank(int weekOffset, int offset) {
        if (offset == 0) return List.of();
        String[] week = WeekUtil.getWeekStarAndEnd(weekOffset);
        if (offset > 0) {
            return timeMapper.queryLimitRankTopReport(week[0], week[1], offset);
        } else {
            return timeMapper.queryLimitRankTopReport(week[0], week[1], Math.abs(offset));
        }
    }
}
