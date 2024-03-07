package com.aurora.day.auroratimerservernative.servicelmpl;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.exceptions.TimeServicesException;
import com.aurora.day.auroratimerservernative.exceptions.UserServicesException;
import com.aurora.day.auroratimerservernative.mapper.TargetTimeMapper;
import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import com.aurora.day.auroratimerservernative.mapper.UserTimeMapper;
import com.aurora.day.auroratimerservernative.pojo.*;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.service.ITermService;
import com.aurora.day.auroratimerservernative.service.IUserTimeService;
import com.aurora.day.auroratimerservernative.utils.SchoolCalendarUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTimeServiceImpl implements IUserTimeService {

    private static final Log logger = LogFactory.get();

    private final UserTimeMapper userTimeMapper;
    private final TargetTimeMapper targetTimeMapper;
    private final UserMapper userMapper;
    private final ITermService termService;
    private static boolean Querying = false;

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
            logger.info("用户:" + id + " 今日首次打卡,本周时长:" + resultTime + "秒");
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
            logger.info("用户:" + id + " 打卡成功,本周时长:" + resultTime + "秒");
            if (userTimeMapper.update(userTime, updateWrapper) != 1) throw new TimeServicesException("计时失败");
            return resultTime;
        }
    }

    @Override
    public List<UserOnlineTime> getTimeRank(int x) {
        //先获取第x周的日期范围
        String weekStart = DateUtil.format(CalendarUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -x).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        String weekEnd = DateUtil.format(CalendarUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -x).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        //获取当前学期的日期范围
        Term currentTerm = termService.getCurrentTerm();
        if (currentTerm == null) {
            //防止多个线程同时查询
            synchronized (this) {
                if(currentTerm==null &&!Querying){
                    Querying = true;
                    //尝试从本地校历获取
                    try{
                        TermTime termTime = SchoolCalendarUtil.getTermTimeLocal();
                        if (termTime == null) {
                            try {
                                //从学校官网下载
                                termTime = SchoolCalendarUtil.getTermTime();
                            } catch (Throwable t) {
                                logger.warn("线上获取校历失败:{}", t);
                            }
                        }else {
                            currentTerm = termTime.getCurrentTerm();
                            termService.updateTermTime(termTime);
                        }
                    }catch (Exception ignored){
                        logger.warn(ignored);
                    }
                    finally {
                        Querying=false;
                    }
                }
            }
        }
        if(currentTerm==null){
            //make fake term for temple use
            currentTerm = Term.makeTempleTerm();
            termService.updateTermById(currentTerm);
        }
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

    @Override
    public Long getUserWeekTimeById(String id) {
        Date now = new Date();
        String week_start = DateUtil.beginOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        String week_end = DateUtil.endOfWeek(now).toString(DatePattern.NORM_DATE_PATTERN);
        return userTimeMapper.queryUserWeekTime(id, week_start, week_end);
    }

    @Deprecated
    @Override
    public boolean transferOldTime(String start, String end, String id) {
        return false;
    }

    @Override
    public List<UserTime> queryTime(String id, String start, String end) {
        User user = userMapper.selectById(id);
        if (user == null) throw new UserServicesException(ResponseState.IllegalArgument.replaceMsg("用户不存在"));
        return userTimeMapper.queryTime(user.getId(), start, end);
    }

}
