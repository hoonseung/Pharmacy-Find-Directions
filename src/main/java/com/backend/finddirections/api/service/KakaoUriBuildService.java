package com.backend.finddirections.api.service;


import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;


@Service
public class KakaoUriBuildService {

    private static final String KAKAO_ADDRESS_SEARCH_URI = "https://dapi.kakao.com/v2/local/search/address.json";
    private static final String KAKAO_CATEGORY_SEARCH_URI = "https://dapi.kakao.com/v2/local/search/category.json";


    public URI kakaoAddressUriBuild(String address) {
        return UriComponentsBuilder.fromUriString(KAKAO_ADDRESS_SEARCH_URI)
                .queryParam("query", address)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }


    public URI kakaoCategoryUriBuild(String category, double latitude, double longitude, double radius) {

        double covertToMeterRadius = radius * 1000;

        return UriComponentsBuilder.fromUriString(KAKAO_CATEGORY_SEARCH_URI)
                .queryParam("category_group_code", category)
                .queryParam("x", longitude)
                .queryParam("y", latitude)
                .queryParam("radius", covertToMeterRadius)
                .queryParam("sort", "distance")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
