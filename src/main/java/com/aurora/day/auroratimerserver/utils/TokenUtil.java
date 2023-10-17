package com.aurora.day.auroratimerserver.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类
 */
public class TokenUtil {

    public static String createToken(String uid, long expire_time, boolean isAdmin) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("isAdmin", isAdmin);
                put("id", uid);
                put("expire_time", System.currentTimeMillis() + expire_time);
            }
        };
        return JWTUtil.createToken(map, TimerConfig.getTokenKeyByte());
    }

    public static String createToken(String uid, boolean isAdmin) {
        Map<String, Object> map = new HashMap<String, Object>() {
            private static final long serialVersionUID = 1L;

            {
                put("isAdmin", isAdmin);
                put("user_id", uid);
                put("expire_time", System.currentTimeMillis() + TimerConfig.tokenExpireTime);
            }
        };
        return JWTUtil.createToken(map, TimerConfig.getTokenKeyByte());
    }


    public static boolean Verify(String token) {
        if (StrUtil.isBlankIfStr(token) || token==null) return false;
        long expireTime = JWTUtil.parseToken(token).getPayloads().getLong("expire_time");
        return expireTime == -1 || expireTime > System.currentTimeMillis() && JWTUtil.verify(token, TimerConfig.getTokenKeyByte());
    }
    public static boolean Verify(String token,boolean passExpire) {
        if (StrUtil.isBlankIfStr(token)) return false;
        long expireTime = JWTUtil.parseToken(token).getPayloads().getLong("expire_time");
        if(passExpire) return JWTUtil.verify(token,TimerConfig.getTokenKeyByte());
        return expireTime > System.currentTimeMillis() && JWTUtil.verify(token,TimerConfig.getTokenKeyByte());
    }

    public static boolean VerifyAdmin(HttpServletRequest request) {
        String token = request.getHeader("token");
        if(!Verify(token)) return false;
        return JWTUtil.parseToken(token).getPayloads().getBool("isAdmin",false);
    }

    public static String getId(String token){
        if(Verify(token)){
            return JWTUtil.parseToken(token).getPayloads().getStr("user_id");
        }else {
            return null;
        }
    }

}