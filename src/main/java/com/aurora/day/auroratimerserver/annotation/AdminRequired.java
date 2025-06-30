package com.aurora.day.auroratimerserver.annotation;

import com.aurora.day.auroratimerserver.filter.AdminFilter;
import org.noear.solon.annotation.Addition;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要校验管理员权限的注解
 */
@Addition(AdminFilter.class)
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminRequired {
}
