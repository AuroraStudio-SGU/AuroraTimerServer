package com.aurora.day.auroratimerserver.service;

import com.aurora.day.auroratimerserver.pojo.WeeklyDustList;

public interface IDutyService {

    /**
     * 添加值日名单
     * @param wed 周三值日人员
     * @param sun 周日值日人员
     * @return 是否添加成功
     */
    boolean pushDutyList(String wed,String sun);

    /***
     * 获取数据库中最新的值日表
     * @return {@link WeeklyDustList}
     */
    WeeklyDustList getNewestDust();
}
