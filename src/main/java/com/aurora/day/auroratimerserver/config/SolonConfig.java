package com.aurora.day.auroratimerserver.config;

import org.noear.solon.annotation.Bean;
import org.noear.solon.annotation.Configuration;
import org.noear.solon.annotation.Inject;
import org.noear.solon.data.cache.CacheService;
import org.noear.solon.data.cache.CacheServiceSupplier;

@Configuration
public class SolonConfig {

    @Bean
    public CacheService cache1(@Inject("${solon.cache1}") CacheServiceSupplier supplier) {
        return supplier.get();
    }
}
