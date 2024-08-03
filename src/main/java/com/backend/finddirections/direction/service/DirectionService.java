package com.backend.finddirections.direction.service;


import com.backend.finddirections.api.dto.Document;
import com.backend.finddirections.api.service.KakaoCategorySearchAddress;
import com.backend.finddirections.direction.entity.Direction;
import com.backend.finddirections.direction.repository.DirectionRepository;
import com.backend.finddirections.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3;
    private static final double RADIUS_KM = 10.0;

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchAddress kakaoCategorySearchAddress;

    @Transactional
    public List<Direction> saveDirectionAll(List<Direction> directions) {
        if (CollectionUtils.isEmpty(directions)) return Collections.emptyList();
        return directionRepository.saveAll(directions);
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

        return kakaoCategorySearchAddress.requestCategorySearch(inputDocument.latitude(), inputDocument.longitude(), RADIUS_KM)
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
