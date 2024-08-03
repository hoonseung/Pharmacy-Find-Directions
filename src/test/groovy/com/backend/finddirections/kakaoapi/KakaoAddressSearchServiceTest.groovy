package com.backend.finddirections.kakaoapi


import com.backend.finddirections.api.service.KakaoAddressSearchService
import com.backend.finddirections.pharmacy.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest {


    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService


    def "address 파라미터 값이 null 이면 예외를 발생시킨다"() {
        given:
        String address = null

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result == null
    }


    def "address 파라미터 값이 존재한다면 API 호출에 성공한다"() {
        given:
        String address = "전북 삼성동 100"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.getMeta() != null
        result.getMeta().totalCount() > 0
        result.getDocuments().size() > 0
        result.getDocuments().get(0) != null
    }


    def "정상적인 주소를 입력 했을 경우, 정상적으로 위도 경도가 반환된다"() {
        given:
        boolean actualResult = false

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        if (result == null) actualResult = false
        else actualResult = result.getDocuments().size() > 0


        where:
        inputAddress        | expectedResult
        "전북 삼성동 100"        | true
        "서울 성북구 종암동 91"     | true
        "서울 대학로"            | true
        "서울 성북구 종암동 잘못된 주소" | false

    }


}
