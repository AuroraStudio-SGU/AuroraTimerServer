package com.aurora.day.auroratimerserver.utils;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class WeekUtil {

    public static String[] getCurrentWeekStarAndEnd(){
        return new String[]{"",""};
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
