package com.aurora.day.auroratimerservernative.interceptors;

import com.aurora.day.auroratimerservernative.schemes.eums.PrivilegeEnum;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.service.IUserService;
import com.aurora.day.auroratimerservernative.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class DutyInterceptor implements HandlerInterceptor {

    @Autowired
    IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) return true;//预检请求默认通过
        PrivilegeEnum privEnum = userService.queryPriv(TokenUtil.getId(request.getHeader("token")));
        if(privEnum.equals(PrivilegeEnum.DutyManager) || privEnum.equals(PrivilegeEnum.Admin)){
            return true;
        }else {
            response.sendError(ResponseState.AuthorizationError.getCode(), ResponseState.AuthorizationError.getMsg());
            return false;
        }
    }
}
