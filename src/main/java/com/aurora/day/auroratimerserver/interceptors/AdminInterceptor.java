package com.aurora.day.auroratimerserver.interceptors;

import com.aurora.day.auroratimerserver.schemes.eums.ResponseState;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
