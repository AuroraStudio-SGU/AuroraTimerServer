package com.aurora.day.auroratimerserver.pojo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Term {
    public int days;
    public Date start;
    public Date end;

    @Override
    public String toString() {
        return "Term{" +
                "days=" + days +
                ", start=" + DateUtil.format(start, DatePattern.NORM_DATE_PATTERN) +
                ", end=" + DateUtil.format(end, DatePattern.NORM_DATE_PATTERN) +
                '}';
    }
}
