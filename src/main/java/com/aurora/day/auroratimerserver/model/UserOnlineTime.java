package com.aurora.day.auroratimerserver.model;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Table("user_online_time")
public class UserOnlineTime {
    //学号
    @Id(keyType = KeyType.None)
    private String userId;
    //打卡日期
    @Id(keyType = KeyType.None)
    private LocalDate recordDate;
    //最后打卡时间
    private LocalDateTime lastRecordTime;
    //在线时长(单位秒)
    private Integer onlineTime;

    public UserOnlineTime(String id,int time) {
        this.userId = id;
        this.recordDate = LocalDate.now();
        this.lastRecordTime = LocalDateTime.now();
        this.onlineTime = time;
    }

    public void plusTime(int time) {
        this.onlineTime += time;
        this.lastRecordTime = LocalDateTime.now();
    }
}
