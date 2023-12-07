package com.aurora.day.auroratimerservernative.config;

import com.aurora.day.auroratimerservernative.interceptors.AdminInterceptor;
import com.aurora.day.auroratimerservernative.interceptors.ManagerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**");
        registry.addInterceptor(new ManagerInterceptor()).addPathPatterns("/manager/**");
    }
}
