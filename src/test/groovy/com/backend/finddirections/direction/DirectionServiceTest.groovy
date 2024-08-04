package com.backend.finddirections.direction

import com.backend.finddirections.api.dto.Document
import com.backend.finddirections.api.service.KakaoCategorySearchAddressService
import com.backend.finddirections.direction.repository.DirectionRepository
import com.backend.finddirections.direction.service.Base62Service
import com.backend.finddirections.direction.service.DirectionService
import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes
import com.backend.finddirections.pharmacy.service.PharmacySearchService
import spock.lang.Specification

import java.time.LocalDateTime

class DirectionServiceTest extends Specification {

    private DirectionRepository directionRepository = Mock()
    private KakaoCategorySearchAddressService kakaoCategorySearchAddressService = Mock()
    private Base62Service base62Service = Mock()

    private PharmacySearchService pharmacySearchService = Mock()

    private DirectionService directionService = new DirectionService(pharmacySearchService, directionRepository,
            kakaoCategorySearchAddressService, base62Service)

    private List<PharmacyRes> pharmacyResList


    def setup() {
        pharmacyResList = new ArrayList<>()
        pharmacyResList.addAll(
                PharmacyRes.builder()
                        .id(1L)
                        .pharmacyName("돌곶이온누리약국")
                        .pharmacyAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .createdDate(LocalDateTime.now())
                        .build(),
                PharmacyRes.builder()
                        .id(2L)
                        .pharmacyName("호수온누리약국")
                        .pharmacyAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .createdDate(LocalDateTime.now())
                        .build()
        )
    }


    def "buildDirectionList 결과 값이 거리 순으로 정렬되는지 검증"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def document = Document.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyResList() >> pharmacyResList // stubbing
        def results = directionService.buildDirectionList(document)


        then:
        results.size() == 2
        println results.get(0).getTargetPharmacyName() == "호수온누리약국"
        println results.get(1).getTargetPharmacyName() == "돌곶이온누리약국"
    }


    def "buildDirectionList 정해진 반경 10KM 내에 검색되는지 확인"() {
        given:
        pharmacyResList.add(
                PharmacyRes.builder()
                        .id(3L)
                        .pharmacyName("경기약국")
                        .pharmacyAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .createdDate(LocalDateTime.now())
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def document = Document.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyResList() >> pharmacyResList
        def results = directionService.buildDirectionList(document)

        then:
        results.size() == 2
    }


}