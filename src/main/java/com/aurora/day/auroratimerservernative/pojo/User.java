package com.aurora.day.auroratimerservernative.pojo;


import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@TableName("user")
@AllArgsConstructor
public class User {
    private String id;
    private String name;
    private String password;
    private String avatar;
    private boolean admin;
    private boolean afk;
    private long reduceTime;
    private int unfinishedCount;
    private String major;
    private String grade;
    private String workGroup;
    private int priv;

    public User() {
        this.avatar = TimerConfig.avatarDefaultUrl;
        this.admin = false;
        this.afk = false;
        this.reduceTime = 0L;
        this.unfinishedCount = 0;
        this.priv = 0;
    }


}
