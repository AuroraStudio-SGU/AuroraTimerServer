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
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@TableName("term")
@AllArgsConstructor
public class Term implements Serializable {
    @TableId(value = "id")
    private Integer id;
    public Integer days;
    public Date start;
    public Date end;
    @TableField("update_time")
    public Date updateTime;
    public String name;


    public Term(int id,Integer days, Date start, Date end,String name) {
        this.id = id;
        this.days = days;
        this.start = start;
        this.end = end;
        this.updateTime = new Date();
        this.name = name;
    }
    public Term(Date start, Date end,int days) {
        this.days = days;
        this.start = start;
        this.end = end;
        this.updateTime = new Date();
    }

    public Term(int id,String name){
        this.id = id;
        this.name = name;
        this.updateTime = new Date();
    }

    /**
     * 返回一个认为编写的学期，应急使用
     * @return
     */
    public static Term makeTempleTerm(){
        int termId = 1;
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        int year = start.get(Calendar.YEAR);
        int month = start.get(Calendar.MONTH);
        String name = year + "学年(第一学期)";
        if(month>8){
            start.set(Calendar.MONTH,Calendar.SEPTEMBER);
            start.set(Calendar.DAY_OF_MONTH,1);
            end.set(Calendar.YEAR,year+1);
            end.set(Calendar.MONTH,1);
            end.set(Calendar.DAY_OF_MONTH,31);
        }else {
            termId = 2;
            name = year-1 + "学年(第二学期)";
            start.set(Calendar.MONTH,Calendar.FEBRUARY);
            start.set(Calendar.DAY_OF_MONTH,20);
            end.set(Calendar.MONTH,Calendar.JUNE);
            end.set(Calendar.DAY_OF_MONTH,30);
        }
        Term fakeTerm = new Term(termId,name);
        fakeTerm.start = start.getTime();
        fakeTerm.end = end.getTime();
        long days = DateUtil.betweenDay(fakeTerm.start,fakeTerm.end,false);
        if(days>Integer.MAX_VALUE){
            fakeTerm.days = Integer.MAX_VALUE;
        }else {
            fakeTerm.days = (int)days;
        }
        return fakeTerm;
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
