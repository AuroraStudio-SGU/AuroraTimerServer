package com.aurora.day.auroratimerserver.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice_list")
public class Notice {
    @TableId("user_id")
    private String userId;

    private String notice;
    private Date targetTime;

    private Date updateTime;
}
