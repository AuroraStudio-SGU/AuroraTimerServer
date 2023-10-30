package com.aurora.day.auroratimerserver;

import com.aurora.day.auroratimerserver.config.TimerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(TimerConfig.class)
@EnableTransactionManagement
public class AuroraTimerServerApplication {
    public static void main(String[] args) {SpringApplication.run(AuroraTimerServerApplication.class, args);}
}
