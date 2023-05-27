package com.aurora.day.auroratimerserver.controller;

import cn.hutool.core.util.ClassUtil;
import com.aurora.day.auroratimerserver.config.TimerConfig;
import com.aurora.day.auroratimerserver.schemes.R;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {


    @RequestMapping("/test")
    public R test() throws InstantiationException, IllegalAccessException {
        Class<TimerConfig> clazz = TimerConfig.class;
        List<Object> values = new ArrayList<>();
        for (Field declaredField : clazz.getDeclaredFields()) {
            values.add(declaredField.get(clazz.newInstance()));
        }
        return R.OK(values);
    }

}
