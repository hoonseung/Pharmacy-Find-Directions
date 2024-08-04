package com.backend.finddirections.direction.service;


import com.backend.finddirections.api.dto.Document;
import com.backend.finddirections.api.service.KakaoCategorySearchAddress;
import com.backend.finddirections.direction.entity.Direction;
import com.backend.finddirections.direction.repository.DirectionRepository;
import com.backend.finddirections.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchAddress kakaoCategorySearchAddress;
    private final Base62Service base62Service;

    @Transactional
    public List<Direction> saveDirectionAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) return Collections.emptyList();
        return directionRepository.saveAll(directions);
    }


    public Direction findById(String encodedId) {
        Long directionId = base62Service.decodeDirectionId(encodedId);
        return directionRepository.findById(directionId)
                .orElseThrow(() -> new RuntimeException("direction 을 찾을 수 없습니다"));
    }


    public String getDirectionUrlByEncodedId(String encodedId) {
        Direction direction = findById(encodedId);
        return buildDirectionUrl(direction);
    }

    private String buildDirectionUrl(Direction direction) {
        String param = String.join(",", direction.getTargetPharmacyName(), String.valueOf(direction.getTargetLatitude()),
                String.valueOf(direction.getTargetLongitude()));
        return UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + param).toUriString();
    }


    public List<Direction> buildDirectionList(Document inputDocument) {
        if (Objects.isNull(inputDocument)) {
            return Collections.emptyList();
        }

        return pharmacySearchService.searchPharmacyResList()
                .stream()
                .map(pharmacy -> Direction.builder()
                        .inputAddress(inputDocument.addressName())
                        .inputLatitude(inputDocument.latitude())
                        .inputLongitude(inputDocument.longitude())
                        .targetPharmacyName(pharmacy.pharmacyName())
                        .targetAddress(pharmacy.pharmacyAddress())
                        .targetLatitude(pharmacy.latitude())
                        .targetLongitude(pharmacy.longitude())
                        .distance(calculateDistance(inputDocument.latitude(), inputDocument.longitude(),
                                pharmacy.latitude(), pharmacy.longitude()))
                        .build())
                .filter(pharmacy -> pharmacy.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .toList();
    }


    public List<Direction> buildDirectionListByCategoryApi(Document inputDocument) {
        if (Objects.isNull(inputDocument)) {
            return Collections.emptyList();
        }

        List<Direction> list = kakaoCategorySearchAddress.requestCategorySearch(inputDocument.latitude(), inputDocument.longitude(), RADIUS_KM)
                .getDocuments()
                .stream()
                .map(documentDto -> Direction.builder()
                        .inputAddress(inputDocument.addressName())
                        .inputLatitude(inputDocument.latitude())
                        .inputLongitude(inputDocument.longitude())
                        .targetPharmacyName(documentDto.placeName())
                        .targetAddress(documentDto.addressName())
                        .targetLatitude(documentDto.latitude())
                        .targetLongitude(documentDto.longitude())
                        .distance(documentDto.distance() * 0.001)
                        .build())
                .limit(MAX_SEARCH_COUNT)
                .toList();
        log.info("list : {}", list);
        return list;
    }


    // Haversine formula
    // 두 위도 경도 사이의 거리를 구하는 알고리즘
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
