package com.aurora.day.auroratimerservernative;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.aurora.day.auroratimerservernative.mapper")
public class AuroraTimerServerNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraTimerServerNativeApplication.class, args);
    }

}
