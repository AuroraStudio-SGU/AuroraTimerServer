package com.aurora.day.auroratimerserver.utils;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWTUtil;
import org.noear.solon.core.handle.Context;

import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Token工具类
 */
public class TokenUtil {

    private static final String key = "AuroraTimer";

    public static String createToken(String uid, boolean isAdmin) {
        Map<String, Object> map = new HashMap<String, Object>() {
            @Serial
            private static final long serialVersionUID = 1L;

            {
                put("isAdmin", isAdmin);
                put("user_id", uid);
            }
        };
        return JWTUtil.createToken(map, key.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean Verify(String token) {
        if (StrUtil.isBlankIfStr(token) || token == null) return false;
        return JWTUtil.verify(token, key.getBytes(StandardCharsets.UTF_8));
    }

    public static boolean VerifyAdmin(Context request) {
        String token = request.header("token");
        if (!Verify(token)) return false;
        return JWTUtil.parseToken(token).getPayloads().getBool("isAdmin", false);
    }

    public static String getId(String token) {
        if (Verify(token)) {
            return JWTUtil.parseToken(token).getPayloads().getStr("user_id");
        } else {
            return null;
        }
    }

}