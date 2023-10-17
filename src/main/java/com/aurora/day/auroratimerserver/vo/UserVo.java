package com.aurora.day.auroratimerserver.vo;

import cn.hutool.core.annotation.Alias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo {
    private String id;
    private String name;
    private String avatar;
    private boolean admin;
    private long currentWeekTime;
    private String token;
    private String major;
    private String grade;
    @Alias("workGroup")
    private String work_group;
}
