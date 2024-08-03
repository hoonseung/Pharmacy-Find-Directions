package com.backend.finddirections.pharmacy.service;

import com.backend.finddirections.api.dto.Document;
import com.backend.finddirections.api.dto.KakaoApiResponse;
import com.backend.finddirections.api.service.KakaoAddressSearchService;
import com.backend.finddirections.direction.entity.Direction;
import com.backend.finddirections.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;


    public void recommendationPharmacyList(String address) {

        KakaoApiResponse kakaoApiResponse = kakaoAddressSearchService.requestAddressSearch(address);
        if (Objects.isNull(kakaoApiResponse) || kakaoApiResponse.getDocuments().isEmpty()) {
            log.error("[PharmacyRecommendationService recommendationPharmacyList fail] Input address: {}", address);
            return;
        }
        Document document = kakaoApiResponse.getDocuments().getFirst();

        //List<Direction> directions = directionService.buildDirectionList(document);
        List<Direction> directions = directionService.buildDirectionListByCategoryApi(document);

        directionService.saveDirectionAll(directions);
    }
}
