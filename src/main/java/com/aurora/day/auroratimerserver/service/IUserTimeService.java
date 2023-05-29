package com.aurora.day.auroratimerserver.service;


import com.aurora.day.auroratimerserver.vo.UserOnlineTime;
import com.aurora.day.auroratimerserver.vo.UserTimeVo;

import java.util.List;

public interface IUserTimeService {
    /**
     * 给用户添加计时时长
     * @param id 用户id
     * @param time 添加的时间
     * @return 是否添加成功
     */
    boolean addTime(String id,int time);
    /**
     * 获取前x周的打卡列表
     * @param x 第前x周
     * @return 打卡列表
     */
    List<UserOnlineTime> getTimeRank(int x);

}
