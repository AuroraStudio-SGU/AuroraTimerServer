package com.aurora.day.auroratimerserver.utils;

import cn.hutool.core.date.CalendarUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;

public class WeekUtil {

    public static String[] getCurrentWeekStarAndEnd(){
        return getWeekStarAndEnd(0);
    }
    public static String[] getWeekStarAndEnd(int offset){
        String weekStart = DateUtil.format(CalendarUtil.beginOfWeek(DateUtil.offsetWeek(DateUtil.date(), -offset).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        String weekEnd = DateUtil.format(CalendarUtil.endOfWeek(DateUtil.offsetWeek(DateUtil.date(), -offset).toCalendar()).getTime(), DatePattern.NORM_DATE_PATTERN);
        return new String[]{weekStart,weekEnd};
    }


    /**
     * 获得当前周标识符
     * @return 例如 "202413" 2024年第13周
     */
    public static int getCurrentWeekIdentifier(){
        LocalDate now = LocalDate.now();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = now.get(weekFields.weekOfWeekBasedYear());
        return now.getYear()*100+weekNumber;
    }
    /**
     * 根据本周偏移量获得周标识符
     * @param offset 偏移量，正值向历史偏移
     * @return 例如 "202413" 2024年第13周
     */
    public static int getWeekIdentifierByOffset(int offset){
        LocalDate date = DateUtil.offsetWeek(new Date(), -offset).toLocalDateTime().toLocalDate();
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
        return date.getYear()*100+weekNumber;
    }

    /**
     * 获得当前学期标识符
     * @return 例如"20241"为2024第一学期(2023-9~2024-2)
     */
    public static int getCurrentTermIdentifier(){
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthValue();
        if(month >= 9 || month < 2){
            return year*10+1;
        }
        return year*10+2;
    }
}
