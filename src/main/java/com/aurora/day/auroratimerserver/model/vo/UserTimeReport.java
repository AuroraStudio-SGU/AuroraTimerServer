package com.aurora.day.auroratimerserver.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserTimeReport extends UserVo {
    //本周总打卡时长
    private long weekTime;
    //本周减时时长（单位小时）
    private BigDecimal reducedTime;
    //本学期总打打卡时长
    private long totalTime;
}
