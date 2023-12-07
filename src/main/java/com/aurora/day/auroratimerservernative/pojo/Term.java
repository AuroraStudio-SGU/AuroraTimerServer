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

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("term")
public class Term implements Serializable {
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

    //编一个勉强能用的
    public static Term makeTempleTerm(){
        Term term = new Term();
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        Calendar end = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        start.set(Calendar.DAY_OF_MONTH,1);
        end.set(Calendar.DAY_OF_MONTH,1);
        if(month>=2 && month<=7){//new 第二学期
            start.set(Calendar.MONTH,2);
            end.set(Calendar.MONTH,7);
        }else {
            if(month>=9){
                end.set(Calendar.YEAR,year+1);
                start.set(Calendar.MONTH,9);
            }else {
                start.set(Calendar.YEAR,year-1);
            }
            end.set(Calendar.MONTH,1);
        }
        term.start = start.getTime();
        term.end = end.getTime();
        return term;
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
