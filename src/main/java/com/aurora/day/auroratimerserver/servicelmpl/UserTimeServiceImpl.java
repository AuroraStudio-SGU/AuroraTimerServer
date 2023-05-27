package com.aurora.day.auroratimerserver.servicelmpl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.exceptions.TimeServicesException;
import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.mapper.UserTimeMapper;
import com.aurora.day.auroratimerserver.pojo.UserTime;
import com.aurora.day.auroratimerserver.service.IUserTimeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class UserTimeServiceImpl implements IUserTimeService {

    private static final Log logger = LogFactory.get();

    private final UserMapper userMapper;
    private final UserTimeMapper userTimeMapper;

    @Override
    public boolean addTime(String id, int time) {
        QueryWrapper<UserTime> wrapper = new QueryWrapper<>();
        wrapper.eq(StrUtil.isBlankIfStr(id), "user_id", id)
                .eq("record_date", DateUtil.today());
        UserTime userTime = userTimeMapper.selectOne(wrapper);
        String today = DateUtil.today();
        String todayTime = DateUtil.now();
        long now = System.currentTimeMillis();
        //若今天没有登录
        if (userTime == null) {
            userTime = new UserTime(id, today, todayTime, time);
            return userTimeMapper.insert(userTime) == 1;
        } else {
            long recordTime = userTime.getLastRecordTime().getTime();
            long intervalTime = now - recordTime;
            //若有人尝试在同个时间隔重复进行加时操作，则回拒
            if (intervalTime < time) throw new TimeServicesException("请勿重复计时!");
            if (intervalTime > TimerConfig.intervalTime) {
                //正常情况，两次请求在设定间隔时间内(例如断线等网络波动没有持续更新在线时间)
                //这种时候对打卡时间进行补时操作(此时添加的时间与time参数无关)
                long onlineTime = userTime.getOnlineTime() + intervalTime;
                userTime.setLastRecordTime(new Date(now));
                userTime.setOnlineTime(onlineTime);
                logger.info("用户:" + id + " 添加计时时长:" + (onlineTime / 1000) + "秒");
                return userTimeMapper.updateById(userTime) == 1;
            } else {
                //间隔时间过长，则判定为重新上线，不进行补时(增加参数time时间)
                long onlineTime = userTime.getOnlineTime() + time * 1000L;
                userTime.setLastRecordTime(new Date(now));
                userTime.setOnlineTime(onlineTime);
                logger.info("用户:" + id + " 添加计时时长:" + time + "秒");
                return userTimeMapper.updateById(userTime) == 1;
            }
        }
    }
}
