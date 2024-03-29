package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.exceptions.TimeServicesException;
import com.aurora.day.auroratimerserver.exceptions.UserServicesException;
import com.aurora.day.auroratimerserver.mapper.OldUserTimeMapper;
import com.aurora.day.auroratimerserver.mapper.TargetTimeMapper;
import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.mapper.UserTimeMapper;
import com.aurora.day.auroratimerserver.pojo.*;
import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import com.aurora.day.auroratimerserver.service.IUserTimeService;
import com.aurora.day.auroratimerserver.utils.SchoolCalendarUtil;
import com.aurora.day.auroratimerserver.vo.UserOnlineTime;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTimeServiceImpl implements IUserTimeService {

    private static final Log logger = LogFactory.get();

    private final UserTimeMapper userTimeMapper;
    private final TargetTimeMapper targetTimeMapper;
    private final OldUserTimeMapper oldUserTimeMapper;
    private final UserMapper userMapper;

    @Override
    public long addTime(String id, int time) {
        String today = DateUtil.today();
        QueryWrapper<UserTime> wrapper = new QueryWrapper<>();

        //查询今天上线记录
        wrapper.eq("user_id", id)
                .eq("record_date", today);
        UserTime userTime = userTimeMapper.selectOne(wrapper);

        String todayTime = DateUtil.now();
        long now = System.currentTimeMillis();
        long resultTime = time;
        //若今天没有登录
        if (userTime == null) {
            userTime = new UserTime(id, today, todayTime, time);
            if (userTimeMapper.insert(userTime) != 1) throw new TimeServicesException("计时失败");
            logger.info("用户:" + id + " 今日首次打卡:" + resultTime + "秒");
            return resultTime;
        } else {
            UpdateWrapper<UserTime> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("user_id", id);
            updateWrapper.eq("record_date", today);

            long recordTime = userTime.getLastRecordTime().getTime();
            long intervalTime = now - recordTime;
            if (intervalTime < TimerConfig.intervalTime * 1000L) {
                //正常情况，两次请求在设定间隔时间内(例如断线等网络波动没有持续更新在线时间)
                //这种时候对打卡时间进行补时操作(此时添加的时间与time参数无关)
                resultTime = userTime.getOnlineTime() + (intervalTime / 1000);
                userTime.setLastRecordTime(new Date(now));
                userTime.setOnlineTime(resultTime);
            } else {
                //间隔时间过长，则判定为重新上线，不进行补时(增加参数time时间)
                resultTime = userTime.getOnlineTime() + time;
                userTime.setLastRecordTime(new Date(now));
                userTime.setOnlineTime(resultTime);
            }
            updateWrapper.set("online_time", resultTime);
            logger.info("用户:" + id + " 添加计时时长:" + resultTime + "秒");
            if (userTimeMapper.update(userTime, updateWrapper) != 1) throw new TimeServicesException("计时失败");
            return resultTime;
        }
    }

    @Override
    public List<UserOnlineTime> getTimeRank(int x) {
        //先获取第x周的日期范围
        String weekStart = DateUtil.format(CalendarUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -x).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        String weekEnd = DateUtil.format(CalendarUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -x).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        //TODO 校历功能完善后改掉写死日期的方式
        //获取当前学期的日期范围
        TermTime termTime = SchoolCalendarUtil.getTermTimeLocal();
        Term currentTerm = termTime.getCurrentTerm();
        //TODO if Term is null
        String TermStart = DateUtil.format(currentTerm.start, DatePattern.NORM_DATE_PATTERN);
        String TermEnd = DateUtil.format(currentTerm.end, DatePattern.NORM_DATE_PATTERN);
        return userTimeMapper.getRankTime(TermStart, TermEnd, weekStart, weekEnd);
    }

    @Override
    public TargetTime getTargetTime() {
        QueryWrapper<TargetTime> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time").last("limit 1");
        return targetTimeMapper.selectOne(wrapper);
    }

    @Override
    public boolean setTargetTime(float targetTime) {
        return targetTimeMapper.insert(new TargetTime(targetTime)) == 1;
    }

    @Nullable
    @Override
    public Long getUserWeekTimeById(String id) {
        Date now = new Date();
        String week_start = DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        String week_end = DateUtil.endOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        return userTimeMapper.queryUserWeekTime(id, week_start, week_end);
    }

    @Override
    public boolean transferOldTime(String start, String end,String id) {
        if (start == null || end == null) {
            TermTime termTime = SchoolCalendarUtil.getTermTime();
            if (termTime == null) {
                logger.error("学历获取失败");
                return false;
            }
            start = DateUtil.format(termTime.getCurrentTerm().start, DatePattern.NORM_DATE_PATTERN);
            end = DateUtil.format(termTime.getCurrentTerm().end, DatePattern.NORM_DATE_PATTERN);
        }
        boolean success = true;
        try {
            //获取旧的打卡数据
            DynamicDataSourceContextHolder.push("oldtimer");
            QueryWrapper<OldUserTime> wrapper = new QueryWrapper<>();
            wrapper.eq(id!=null,"user_id",id);
            wrapper.between("today_date",start,end);
            List<OldUserTime> list = oldUserTimeMapper.selectList(wrapper);
            DynamicDataSourceContextHolder.poll();
            for (OldUserTime userTime : list) {
                UserTime NewTime = new UserTime();
                NewTime.setUserId(userTime.getId());
                NewTime.setRecordDate(userTime.getTodayDate());
                NewTime.setLastRecordTime(new Date(userTime.getTodayDate().getTime()+userTime.getLastOnlineTime().getTime()));
                NewTime.setOnlineTime(userTime.getTodayOnlineTime() / 1000L);
                userTimeMapper.insert(NewTime);
            }
        } catch (Throwable t) {
            logger.error("数据转移异常");
            logger.error(t);
            success = false;
        }
        return success;
    }

    @Override
    public List<UserTime> queryTime(String id, String start, String end) {
        User user = userMapper.selectById(id);
        if (user == null) throw new UserServicesException(ResponseState.IllegalArgument.replaceMsg("用户不存在"));
        return userTimeMapper.queryTime(user.getId(),start,end);
    }

}
