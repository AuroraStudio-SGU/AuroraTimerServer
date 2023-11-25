package com.aurora.day.auroratimerservernative.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "timer")
public class TimerConfig {
    /***
     * 补时间隔时间，单位 秒(s)<br>
     * 默认值:900
     */
    public static int intervalTime;

    /***
     * id生成器所生成的id长度<br>
     * 默认值:16
     */
    public static int idLength;

    /***
     * 公告存放个数，超过个数后开始删除旧的公告<br>
     * 默认值:10
     */
    public static int noticeSize;
    /***
     * 值日表存放个数,超过该数则开始删除旧的值日<br>
     * 默认值:10
     */
    public static int DutySize;

    /***
     * 默认头像地址<br>
     * 默认值:<a href="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png">默认头像连接</a>
     */
    public static String avatarDefaultUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";

    /***
     * Token过期时间,单位 毫秒(ms)<br>
     * 默认值:-1<br>
     * 设置成-1时则不检测过期时间
     */
    public static long tokenExpireTime;
    /***
     * Token秘钥,用于生成token<br>
     * 默认值:"AuroraTimer"
     */
    public static String tokenKey;
    /***
     * 文件存放目录,用于存放各种文件的目录,不同系统环境下不兼容,需要自行设置<br>
     * 没有默认值!!!!
     */
    public static String filePath;

    /***
     * 面向访问者的hostIp地址,用于文件链接创建<br>
     * 默认值:192.168.49.66
     */
    public static String publicHost;
    /***
     * 是否保持上周的目标时间;
     */
    public static boolean keepLastWeekTargetTime = true;

    @Value("${timer.public-host:192.18.49.66}")
    public void setPublicHost(String publicHost) {
        TimerConfig.publicHost = publicHost;
    }

    //    @Value("${timer.filePath:/home/auroratimer}")
    @Value("${timer.filePath}")
    public void setFilePath(String filePath) {
        TimerConfig.filePath = filePath;
    }

    @Value("${timer.token-key:AuroraTimer}")
    public void setTokenKey(String tokenKey) {
        TimerConfig.tokenKey = tokenKey;
    }
    @Value("${timer.interval-time:900}")
    public void setIntervalTime(int intervalTime) {
        TimerConfig.intervalTime = intervalTime;
    }
    @Value("${timer.avatar-default-url:https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png}")
    public void setAvatarDefaultUrl(String avatarDefaultUrl) {
        TimerConfig.avatarDefaultUrl = avatarDefaultUrl;
    }
    @Value("${timer.token-expire-time:-1}")
    public void setTokenExpireTime(long tokenExpireTime) {
        TimerConfig.tokenExpireTime = tokenExpireTime;
    }
    @Value("${timer.id-length:16}")
    public void setIdLength(int idLength) {
        TimerConfig.idLength = idLength;
    }
    @Value("${timer.notice-size:10}")
    public void setNoticeSize(int noticeSize) {
        TimerConfig.noticeSize = noticeSize;
    }
    @Value("${timer.duty-size:10}")
    public void setDutySize(int dutySize) {
        DutySize = dutySize;
    }

    public static byte[] getTokenKeyByte(){
        return TimerConfig.tokenKey.getBytes();
    }

}
