package com.aurora.day.auroratimerservernative.config;

import com.aurora.day.auroratimerservernative.interceptors.AdminInterceptor;
import com.aurora.day.auroratimerservernative.interceptors.DutyInterceptor;
import com.aurora.day.auroratimerservernative.interceptors.ManagerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AdminInterceptor adminInterceptor;
    @Autowired
    ManagerInterceptor managerInterceptor;
    @Autowired
    DutyInterceptor dutyInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor).addPathPatterns("/admin/**");
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager/**");
        registry.addInterceptor(dutyInterceptor).addPathPatterns("/duty/**");
    }
}
