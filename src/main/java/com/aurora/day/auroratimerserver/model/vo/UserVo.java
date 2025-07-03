package com.aurora.day.auroratimerserver.model.vo;

import com.aurora.day.auroratimerserver.model.User;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserVo {
    //学号
    private String id;
    //姓名
    private String name;
    //年级
    private String garde;
    //组别
    private String workGroup;
    //专业
    private String major;
    //头像url
    private String avatar;
    //权限值
    private Integer priv;
    //是否为管理员
    private Boolean admin;
    //是否退休
    private Boolean afk;
    //未完成打卡目标次数
    private Integer unfinishedCount;
    //token
    private String token;
}
