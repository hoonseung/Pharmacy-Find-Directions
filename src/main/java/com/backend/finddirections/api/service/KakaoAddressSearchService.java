package com.backend.finddirections.api.service;

import com.backend.finddirections.api.dto.KakaoApiResponse;
import com.backend.finddirections.api.exception.KakaoClientApiException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoAddressSearchService {

    private final RestClient restClient;
    @Value("${kakao.rest.api.key}")
    private String kakaoKey;
    private final KakaoUriBuildService kakaoUriBuildService;


    @Retryable(
            retryFor = {RuntimeException.class},
            maxAttempts = 2,
            backoff = @Backoff(2000L)
    )
    public KakaoApiResponse requestAddressSearch(String address) {
        if (ObjectUtils.isEmpty(address)) {
            return null;
        }
        URI uri = kakaoUriBuildService.kakaoAddressUriBuild(address);

        return restClient.get()
                .uri(uri)
                .header(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoKey)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, ((request, response) -> {
                    throw new KakaoClientApiException(response.getStatusText(), response.getStatusCode().value());
                }
                ))
                .body(KakaoApiResponse.class);
    }


    @Recover
    public KakaoApiResponse recover(RuntimeException e, String address) {
        log.error("All the retries failed. address: {}, error: {}", address, e.getMessage());
        return null;
    }

}
