package com.github.chMatvey.springBootStarter.starter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IronConfig {

    @Bean
    @ConditionalOnProduct
    public StartListener startListener() {
        return new StartListener();
    }
}
