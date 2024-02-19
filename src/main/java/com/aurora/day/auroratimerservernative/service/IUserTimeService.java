package com.aurora.day.auroratimerservernative.service;


import com.aurora.day.auroratimerservernative.pojo.TargetTime;
import com.aurora.day.auroratimerservernative.pojo.UserTime;
import com.aurora.day.auroratimerservernative.pojo.UserOnlineTime;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface IUserTimeService {
    /**
     * 给用户添加计时时长
     *
     * @param id   用户id
     * @param time 添加的时间,单位秒
     * @return 目前计时时长，单位秒
     */
    long addTime(@NotNull String id, int time);

    /**
     * 获取前x周的打卡列表
     *
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

    /***
     * 将旧的数据迁移到新数据库当中
     * @param start 开始时间
     * @param end 结束时间
     * @param id 是否指定成员
     * @return 是否成功
     */
    @Deprecated
    boolean transferOldTime(String start, String end,String id);

    /**
     * 查询某人从start到end的打卡详情。(只算有计时的日子)
     * @param id id
     * @param start 开始日期
     * @param end 结束日期
     * @return list
     */
    List<UserTime> queryTime(String id,String start,String end);
}
