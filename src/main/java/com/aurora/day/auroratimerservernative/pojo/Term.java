package com.aurora.day.auroratimerservernative.pojo;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("term")
public class Term {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(exist = false)
    public Integer days;
    public Date start;
    public Date end;


    public Term(int days, Date start, Date end) {
        this.days = days;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Term{" +
                "days=" + days +
                ", start=" + DateUtil.format(start, DatePattern.NORM_DATE_PATTERN) +
                ", end=" + DateUtil.format(end, DatePattern.NORM_DATE_PATTERN) +
                '}';
    }
}
