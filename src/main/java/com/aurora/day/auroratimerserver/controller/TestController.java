package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.util.ClassUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.schemes.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {


    //输出所有TimerConfig内的成员属性。
    @RequestMapping("/test")
    public R test() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Class<TimerConfig> clazz = TimerConfig.class;
        List<Object> values = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            values.add(declaredField.get(clazz.getDeclaredConstructor().newInstance()));
        }
        return R.OK(values);
    }

}
