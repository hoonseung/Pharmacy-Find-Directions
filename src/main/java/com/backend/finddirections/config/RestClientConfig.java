package com.backend.finddirections.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {


    //https://dapi.kakao.com/v2/local/search/address.json?query=전북 삼성동 100
    @Bean
    public RestClient restClient() {
        return RestClient.create();
    }
}
