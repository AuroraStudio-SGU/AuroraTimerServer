package com.aurora.day.auroratimerserver.vo;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String id;//学号
    private String name;//姓名
    private String avatar;//头像地址
    private boolean admin;//是否为管理员
    private long currentWeekTime;//本周打卡时长
    private boolean afk;//是否退休
    private String token;//token
    private String major;//专业
    private String grade;//年级
    @Alias("workGroup")
    private String work_group;//方向
}
