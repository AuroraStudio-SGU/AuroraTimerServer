package com.aurora.day.auroratimerservernative;

import com.aurora.day.auroratimerservernative.mapper.UserMapper;
import com.aurora.day.auroratimerservernative.pojo.User;
import org.junit.jupiter.api.Test;
import org.noear.snack.ONode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.PrintWriter;
import java.util.List;

@SpringBootTest
class AuroraTimerServerNativeApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    UserMapper userMapper;
    @Test
    void selectUser(){
        PrintWriter pr = new PrintWriter(System.out);
        List<User> users = userMapper.selectList(null);
        for(User u:users){
            pr.print(ONode.stringify(u));
            pr.flush();
        }
        pr.close();
    }
}
