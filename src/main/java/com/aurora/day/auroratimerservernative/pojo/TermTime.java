package com.aurora.day.auroratimerservernative.pojo;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TermTime {
    public Term first;//2023-02 - 2023-05
    public Term second; // 2023-08 2024-02

    public Term getCurrentTerm(){
        DateTime today = DateUtil.date();
        if(DateUtil.isIn(today,first.start,first.end)) return first;
        if(DateUtil.isIn(today,second.start,second.end)) return second;
        //也有可能都不在,(例如暑假寒假),返回上个学期的
        if(DateUtil.isIn(today,first.end,second.start)) return first;
        Calendar nextYear = Calendar.getInstance();
        nextYear.setTime(first.start);
        nextYear.set(Calendar.YEAR,today.year()+1);
        if(DateUtil.isIn(today,second.end,nextYear.getTime())) return second;
        return Term.makeTempleTerm();
    }
}
