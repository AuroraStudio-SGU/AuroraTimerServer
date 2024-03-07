package com.aurora.day.auroratimerservernative;

import com.aurora.day.auroratimerservernative.config.TimerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableConfigurationProperties(TimerConfig.class)
@EnableTransactionManagement
public class AuroraTimerServerNativeApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuroraTimerServerNativeApplication.class, args);
    }

}
