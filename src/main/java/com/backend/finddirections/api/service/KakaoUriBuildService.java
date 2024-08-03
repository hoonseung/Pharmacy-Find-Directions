package com.backend.finddirections.api.service;


import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Service
public class KakaoUriBuildService {

    private static final String REQUEST_API_URI = "https://dapi.kakao.com/v2/local/search/address.json";


    public URI uriBuild(String address) {
        return UriComponentsBuilder.fromUriString(REQUEST_API_URI)
                .queryParam("query", address)
                .build()
                .toUri();
    }
}
