package com.aurora.day.auroratimerserver.utils;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.pojo.Term;
import com.aurora.day.auroratimerserver.pojo.TermTime;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 用于获取学校校历并分析学期开学和放假的日期(尝试性)
 */
public class SchoolCalendarUtil {
    private static final Log logger = LogFactory.get();

    public static TermTime getTermTime(){
        int year = DateUtil.thisYear();
        String CalendarYear = year-1+"-"+year;
        String PossibleSchoolCaleListUrl = "http://www.sgu.edu.cn/5m1t1l%e6%a0%a1%e5%8e%86.html";
        HttpRequest CaleListPageRequest = HttpUtil.createGet(PossibleSchoolCaleListUrl);
        CaleListPageRequest.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
        String CaleListPage;
        try(HttpResponse response = CaleListPageRequest.execute()) {
            CaleListPage = response.body();
        }
        //TODO if null
        if(CaleListPage==null) return null;
        String calePageUrl = findCalePageUrl(CaleListPage,CalendarYear);
        //TODO if null
        String schoolCaleMatchingUrl = "http://www.sgu.edu.cn/"+calePageUrl;
        HttpRequest CalePageRequest = HttpUtil.createGet(schoolCaleMatchingUrl);
        CalePageRequest.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
        String calePage;
        try(HttpResponse response =  CalePageRequest.execute()){
           calePage  = response.body();
        }
        //TODO if null
        if(calePage==null) return null;
        File caleFile = downloadCale(calePage,CalendarYear);
        if(caleFile==null) {
            //TODO if null
            return null;
        }
        ExcelReader reader = ExcelUtil.getReader(caleFile);
        List<List<Object>> readAll = reader.read();
        List<Object> titles = readAll.stream().filter(list -> ((String) list.get(0)).contains("周历说明")).map(list -> list.get(0)).collect(Collectors.toList());
        return toTerm(titles);
    }

    private static String findCalePageUrl(String html,String CalendarYear){
        return MatchingContext(html, CalendarYear);
    }
    private static File downloadCale(String html,String CalendarYear){
        String matcherResult = MatchingContext(html, CalendarYear);
        if(matcherResult!=null){
            File caleFile = FileUtil.file(TimerConfig.filePath + "\\Calendar"+CalendarYear+".xls");
            long size = HttpUtil.downloadFile(matcherResult,caleFile);
            logger.info("下载校历:"+"下载大小:"+size);
            return caleFile;
        }//TODO if null
        return null;
    }

    private static String  MatchingContext(String html, String CalendarYear) {
        final String textRex = "<a[^>]+?href=\\\"([^\\\"]+)\\\"[^>]*?>(.*?学年校历.*?)</a>";
        Pattern pattern = Pattern.compile(textRex);
        Matcher matcher = pattern.matcher(html);
        String matcherResult = null;
        while (matcher.find()) {
            String href = matcher.group(1);
            String textContent = matcher.group(2);
            if (textContent.contains(CalendarYear)) {
                matcherResult = href.replace("href: ", "");
            }
        }
        return matcherResult;
    }

    private static TermTime toTerm(List<Object> titles){
        String rex = "周历说明:([^\\d]+)(\\d+)天，从(\\d+月\\d+日)至(\\d+月\\d+日)";
        TermTime termTime = new TermTime();
        Map<String, Term> termList = new HashMap<>(2);
        for (Object title : titles) {
            Pattern pattern = Pattern.compile(rex);
            Matcher matcher = pattern.matcher((String) title);
            while (matcher.find()) {
                String type = matcher.group(1);
                int days = Integer.parseInt(matcher.group(2));
                Date start = strToDate(matcher.group(3));
                Date end = strToDate(matcher.group(4));
                termList.put(type,new Term(days,start,end));
            }
        }
        Term summer = termList.get("暑假");
        Term winter = termList.get("寒假");
        //TODO cal days;
        int firstDays = 0;
        int secondDays = 0;
        termTime.first = new Term(firstDays,winter.end,summer.start);
        //TODO 第二个学期还得知道明年的年历 ,先编一个
        int year = DateUtil.thisYear();
        termTime.second = new Term(secondDays,DateUtil.parseDate(year+"-08-01"),DateUtil.parseDate((year+1)+"-02-21"));
        return termTime;
    }

    private static Date strToDate(String uncommonDateStr){
        int year = DateUtil.thisYear();
        String temp = year + "年" + uncommonDateStr;
        DateTime parse = DateUtil.parse(temp, DatePattern.CHINESE_DATE_FORMAT);
        return parse.toJdkDate();
    }

    //读取本地校历文件获得学期情况。
    public static TermTime getTermTimeLocal(){
        int year = DateUtil.thisYear();
        String CalendarYear = year-1+"-"+year;
        File caleFile = FileUtil.file(TimerConfig.filePath + "\\Calendar"+CalendarYear+".xls");
        ExcelReader reader = ExcelUtil.getReader(caleFile);
        List<List<Object>> readAll = reader.read();
        List<Object> titles = readAll.stream().filter(list -> ((String) list.get(0)).contains("周历说明")).map(list -> list.get(0)).collect(Collectors.toList());
        return toTerm(titles);
    }
}
