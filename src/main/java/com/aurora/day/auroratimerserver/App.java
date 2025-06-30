package com.aurora.day.auroratimerserver;

import org.noear.solon.Solon;
import org.noear.solon.annotation.SolonMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SolonMain
public class App {

    static Logger log = LoggerFactory.getLogger("AppEntryPoint");

    public static void main(String[] args) {
        Solon.start(App.class, args);
        log.info("DatabasePassword:{}",Solon.cfg().get("mybatis-flex.datasource.db1.password"));
    }
}