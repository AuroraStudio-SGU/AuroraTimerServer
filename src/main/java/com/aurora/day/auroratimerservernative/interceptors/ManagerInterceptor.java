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

import java.io.IOException;

//其它职位拦截器
@Component
public class ManagerInterceptor implements HandlerInterceptor {
    @Autowired
    IUserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
            return true;//预检请求默认通过
        }
        PrivilegeEnum privilege = userService.queryPriv(TokenUtil.getId(request.getHeader("token")));
        if (privilege == null || privilege.equals(PrivilegeEnum.Normal) || privilege.equals(PrivilegeEnum.Examining)) {
            //这里权限为空也作为正常用户了,先观望一会,等权限完善了再修改(2023/11/27)
            response.sendError(ResponseState.AuthorizationError.getCode(), ResponseState.AuthorizationError.getMsg());
            return false;
        } else {
            return true;
        }
    }
}
