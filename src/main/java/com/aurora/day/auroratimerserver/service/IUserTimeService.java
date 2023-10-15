package com.aurora.day.auroratimerserver.service;


import com.aurora.day.auroratimerserver.pojo.TargetTime;
import com.aurora.day.auroratimerserver.vo.UserOnlineTime;
import com.aurora.day.auroratimerserver.vo.UserTimeVo;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IUserTimeService {
    /**
     * 给用户添加计时时长
     * @param id 用户id
     * @param time 添加的时间,单位秒
     * @return 目前计时时长，单位秒
     */
    long addTime(@NotNull String id, int time);
    /**
     * 获取前x周的打卡列表
     * @param x 第前x周
     * @return 打卡列表
     */
    List<UserOnlineTime> getTimeRank(int x);

    /***
     * 获取目标打卡时长
     * @return TargetTime
     */
    TargetTime getTargetTime();

    /***
     * 设置打卡目标时长
     * @param targetTime 目标时长，单位：小时
     * @return 布尔
     */
    boolean setTargetTime(float targetTime);

    /***
     * 获取用户本周的打卡时间
     * @param id 用户id
     * @return 本周时长，单位：秒。
     */
    Long getUserWeekTimeById(String id);

}
