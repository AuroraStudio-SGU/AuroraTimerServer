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
    private Boolean admin = null;
    private Boolean afk = null;
    private Long reduceTime  = null;
    private Integer unfinishedCount = null;
    private String major;
    private String grade;
    private String workGroup;
    private Integer priv = null;

    public User() {
        this.avatar = TimerConfig.avatarDefaultUrl;
    }
}
