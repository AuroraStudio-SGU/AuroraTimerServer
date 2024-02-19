package com.aurora.day.auroratimerservernative.interceptors;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.aurora.day.auroratimerservernative.schemes.eums.PrivilegeEnum;
import com.aurora.day.auroratimerservernative.schemes.eums.ResponseState;
import com.aurora.day.auroratimerservernative.utils.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

//测试权限用的拦截器
public class ManagerInterceptor implements HandlerInterceptor {

    private static final Log logger = LogFactory.get("PrivManage");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws RuntimeException, IOException {
        if (request.getMethod().equals("OPTIONS")) {
//            request.getHeader()
            return true;//预检请求默认通过
        }
        PrivilegeEnum privilege = TokenUtil.getPriv(request.getHeader("token"));
        if (privilege == null || privilege.equals(PrivilegeEnum.Normal) || privilege.equals(PrivilegeEnum.Examining)) {
            //这里权限为空也作为正常用户了,先观望一会,等权限完善了再修改(2023/11/27)
            response.sendError(ResponseState.AuthorizationError.getCode(), ResponseState.AuthorizationError.getMsg());
            return false;
        } else {
            return true;
        }
    }
}
