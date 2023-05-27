package com.aurora.day.auroratimerserver.service;


public interface IUserTimeService {
    /**
     * 给用户添加计时时长
     * @param id 用户id
     * @param time 添加的时间
     * @return 是否添加成功
     */
    boolean addTime(String id,int time);



}
