package com.aurora.day.auroratimerserver.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserTimeBuffer {
    //记录日期
    private LocalDate recordDate;
    //本周总时长
    private long weeklyTime;
    //本日时间记录
    private UserOnlineTime onlineTime;
    //上次更新时间
    private long internalUpdatedTime;

    public void plusTime(int time) {
        this.weeklyTime += time;
        if (this.onlineTime != null) {
            this.onlineTime.plusTime(time);
        }
        internalUpdatedTime = System.currentTimeMillis();
    }

    public UserTimeBuffer(long weeklyTime, UserOnlineTime onlineTime) {
        this.recordDate = LocalDate.now();
        this.weeklyTime = weeklyTime;
        this.onlineTime = onlineTime;
        this.internalUpdatedTime = System.currentTimeMillis();
    }
}
