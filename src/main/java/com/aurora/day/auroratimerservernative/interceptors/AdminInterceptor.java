package com.aurora.day.auroratimerservernative.interceptors;

import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException, IOException {
        if (request.getMethod().equals("OPTIONS")) return true;//预检请求默认通过
        if (TokenUtil.VerifyAdmin(request)) return true;
        else {
            response.sendError(ResponseState.AuthorizationError.getCode(),ResponseState.AuthorizationError.getMsg());
            return false;
        }
    }
}
