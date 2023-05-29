package com.aurora.day.auroratimerserver.interceptors;

import com.aurora.day.auroratimerserver.exceptions.AuthorityException;
import com.aurora.day.auroratimerserver.utils.TokenUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminInterceptor  implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException {
        if(TokenUtil.NotVerifyAdmin(request)) return true;
        throw new AuthorityException("权限不足");
    }
}
