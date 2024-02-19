package com.aurora.day.auroratimerservernative.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserOnlineTime implements Serializable {
    private String id; //学号
    private String name; //姓名
    private long totalTime; //学期总打打卡时长
    private long weekTime; //本周打开时长
    private long reduceTime; //减时情况
    private int unfinishedCount; //(未使用)未完成目标次数
    private String avatar; //头像地址
    private String grade; //年级
    private String workGroup;//方向
    private int priv;//权限枚举值,具体正在PrivilegeEnum当中。

    public UserOnlineTime(){

    }
}