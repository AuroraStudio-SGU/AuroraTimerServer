package com.aurora.day.auroratimerservernative.controller;

import com.aurora.day.auroratimerservernative.config.TimerConfig;
import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import com.aurora.day.auroratimerservernative.utils.JsonHelper;
import com.aurora.day.auroratimerservernative.pojo.UserVo;
import com.aurora.day.auroratimerservernative.schemes.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.noear.snack.ONode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;

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
    public R testUser() {
        return R.OK(ONode.stringify(userMapper.selectList(null)));
    }

    @GetMapping("/test/view")
    public String viewUser() {
        throw new RuntimeException("a debug exception");
    }

    @GetMapping("/test/view3")
    public R viewUser3() {
        UserVo vo = new UserVo("1", "1", "", false, 0, false, "", "", "", "", 0);
        return R.OK(vo);
    }

    @GetMapping("/test/view5")
    public String viewUser5() throws JsonProcessingException {
        return JsonHelper.objectMapper.valueToTree(new UserVo()).toString();
    }

}
