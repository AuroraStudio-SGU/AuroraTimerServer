package com.aurora.day.auroratimerserver.config;

import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;

import java.math.BigDecimal;

@Configuration
public class TimerConfig {
    //文件路径，用于存放头像等文件。
    @Inject("${timer.filePath:D:\\project}")
    public static String filePath;
    //网络路径，用于将本地资源转换为url路径。
    @Inject("${timer.hostPath:http://localhost:8084/static/}")
    public static String hostPath;
    //默认头像文件名
    @Inject("${timer.defaultAvatarName:DefaultAvatar.png}")
    public static String defaultAvatarName;
    //默认目标打卡时长(单位小时)
    @Inject("${timer.defaultTargetTime:8}")
    public static BigDecimal defaultTargetTime;
    //最多保留的“目标时长"设置记录
    @Inject("${timer.targetTimeCount:4}")
    public static int targetTimeCount;


    public static String getDefaultAvatarPath(){
        return hostPath + defaultAvatarName;
    }
}
