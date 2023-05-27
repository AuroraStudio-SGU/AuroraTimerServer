package com.aurora.day.auroratimerserver.pojo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_online_time")
public class UserTime {
    @TableId("user_id")
    private String userId;
    private Date recordDate;
    private Date lastRecordTime;

    private long onlineTime;

    public UserTime(String userId, String recordDate, String lastRecordTime , long onlineTime) {
        this.userId = userId;
        this.recordDate = DateUtil.parse(recordDate, DatePattern.NORM_DATE_PATTERN);
        this.lastRecordTime = DateUtil.parse(lastRecordTime, DatePattern.NORM_DATETIME_PATTERN);
        this.onlineTime = onlineTime;
    }
}
