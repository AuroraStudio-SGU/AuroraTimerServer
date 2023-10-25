package com.aurora.day.auroratimerserver.pojo;

import cn.hutool.core.bean.BeanUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.vo.UserVo;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
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

    public UserVo toVo() {
        return BeanUtil.toBean(this, UserVo.class);
    }

    public User(String id,String name,String password){
        this.id = id;
        this.name = name;
        this.password = password;
        this.avatar = TimerConfig.avatarDefaultUrl;
        this.admin = false;
        this.afk = false;
        this.reduceTime = 0;
        this.unfinishedCount = 0;
        this.major = "待填写";
        this.grade = "待填写";
        this.workGroup = "待填写";
    }

}
