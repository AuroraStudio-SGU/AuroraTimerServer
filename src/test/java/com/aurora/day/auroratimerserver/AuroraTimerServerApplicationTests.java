package com.aurora.day.auroratimerserver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.mapper.UserMapper;
import com.aurora.day.auroratimerserver.utils.SchoolCalendarUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class AuroraTimerServerApplicationTests {

    @Autowired
    UserMapper userMapper;

    @Test
    void contextLoads() {
        System.out.println(TimerConfig.tokenExpireTime);
    }
    @Test
    void selectAllUsers(){
        userMapper.selectList(null).forEach(System.out::println);
    }


    @Test
    void htmldemo(){
        //        String PossibleSchoolCaleListUrl = "http://www.sgu.edu.cn/5m1t1l%e6%a0%a1%e5%8e%86.html";
//        HttpRequest CaleListPageRequest = HttpUtil.createGet(PossibleSchoolCaleListUrl);
//        CaleListPageRequest.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
//        String CaleListPage = CaleListPageRequest.execute().body();
        String rex = "<a[^>]+?href=\\\"([^\\\"]+)\\\"[^>]*?>(.*?学年校历.*?)</a>";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(NormalTest.html);
        String matcherResult = null;
        String CalendarYear = "2022-2023";
        while (matcher.find()) {
            String href = matcher.group(1);
            String textContent = matcher.group(2);
            if(textContent.contains(CalendarYear)){
                matcherResult =  href.replace("href: ","");
            }
        }
        if(matcherResult==null) return;
//        String schoolCaleMatchingUrl = "http://www.sgu.edu.cn/"+matcherResult;
//        HttpRequest CalePageRequest = HttpUtil.createGet(schoolCaleMatchingUrl);
//        CalePageRequest.header("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.0.0 Safari/537.36");
//        String PageContext = CalePageRequest.execute().body();
        matcher = pattern.matcher(NormalTest.html2);
        matcherResult = null;
        while (matcher.find()) {
            String href = matcher.group(1);
            String textContent = matcher.group(2);
            System.out.println("href: " + href);
            System.out.println("text content: " + textContent);
            if(textContent.contains(CalendarYear)){
                matcherResult =  href.replace("href: ","");
            }
        }
        if(matcherResult==null) return;
        File caleFile = FileUtil.file(TimerConfig.filePath+"\\Calendar2022_2023.xls");
//        long size = HttpUtil.downloadFile(matcherResult,caleFile);
//        System.out.println("Download size: " + size);
        ExcelReader reader = ExcelUtil.getReader(caleFile);
        List<List<Object>> readAll = reader.read();

    }

    @Test
    void getTermTime(){
        System.out.println(SchoolCalendarUtil.getTermTime());
    }
}
