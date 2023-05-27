package com.aurora.day.auroratimerserver.config;

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
     * 默认头像地址<br>
     * 默认值:<a href="https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png">默认头像连接</a>
     */
    public static String avatarDefaultUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";

    /***
     * Token过期时间,单位 毫秒(ms)<br>
     * 默认值:604800000
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

    @Value("${timer.file-path}")
    public void setFilePath(String filePath) {
        TimerConfig.filePath = filePath;
    }

    @Value("${timer.token-key:AuroraTimer}")
    public void setTokenKey(String tokenKey) {
        TimerConfig.tokenKey = tokenKey;
    }

    public long getTokenExpireTime() {
        return tokenExpireTime;
    }
    @Value("${timer.interval-time:900}")
    public void setIntervalTime(int intervalTime) {
        TimerConfig.intervalTime = intervalTime;
    }
    @Value("${timer.avatar-default-url:https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png}")
    public void setAvatarDefaultUrl(String avatarDefaultUrl) {
        TimerConfig.avatarDefaultUrl = avatarDefaultUrl;
    }

    @Value("${timer.token-expire-time:604800000}")
    public void setTokenExpireTime(long tokenExpireTime) {
        TimerConfig.tokenExpireTime = tokenExpireTime;
    }

    public static byte[] getTokenKeyByte(){
        return TimerConfig.tokenKey.getBytes();
    }

}
