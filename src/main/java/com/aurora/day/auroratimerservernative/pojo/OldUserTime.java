package com.aurora.day.auroratimerservernative.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
@TableName("useronlinetime")
public class OldUserTime {
    private String id;
    private Date todayDate;
    private Date lastOnlineTime;
    private long todayOnlineTime;
}
