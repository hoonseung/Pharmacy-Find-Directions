package com.backend.finddirections.api.service;

import com.backend.finddirections.api.dto.KakaoApiResponse;
import com.backend.finddirections.api.exception.KakaoClientApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
public class KakaoAddressSearchService {

    private final RestClient restClient;
    private final String kakaoKey;

    public KakaoAddressSearchService(RestClient restClient,
                                     @Value("${kakao.rest.api.key}")
                                     String kakaoKey) {
        this.restClient = restClient;
        this.kakaoKey = kakaoKey;
    }


    public KakaoApiResponse requestAddressSearch(String address) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("query", address).build())

                .headers(header -> header.setAll(Map.of(
                        "Authorization", kakaoKey,
                        "Content-Type", MediaType.APPLICATION_JSON_VALUE)))

                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new KakaoClientApiException(response.getStatusText(), response.getStatusCode().value());
                }
                ))
                .body(KakaoApiResponse.class);
    }

}
