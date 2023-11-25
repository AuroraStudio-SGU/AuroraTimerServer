package com.aurora.day.auroratimerservernative.controller;

import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import com.aurora.day.auroratimerservernative.schemes.R;
import org.noear.snack.ONode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {


    @Autowired
    TimerConfig timerConfig;

    @Autowired
    UserMapper userMapper;
    //输出所有TimerConfig内的成员属性。
    @RequestMapping("/test")
    public R test() throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return R.OK(ONode.stringify(timerConfig));
    }

    @RequestMapping("/test/user")
    public R testUser(){
        return R.OK(ONode.stringify(userMapper.selectList(null)));
    }
}
