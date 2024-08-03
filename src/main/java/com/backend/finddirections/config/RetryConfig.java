package com.backend.finddirections.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.support.RetryTemplate;


@EnableRetry
@Configuration
public class RetryConfig {

// 직접 등록
//    @Bean
//    public RetryTemplate retryTemplate(){
//        return new RetryTemplate();
//    }
}
