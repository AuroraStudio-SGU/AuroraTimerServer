package com.aurora.day.auroratimerserver.interceptors;

import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException, IOException {
        if (!TokenUtil.NotVerifyAdmin(request)) return true;
        else {
            response.sendError(403, "权限不足");
            return false;
        }
    }
}
