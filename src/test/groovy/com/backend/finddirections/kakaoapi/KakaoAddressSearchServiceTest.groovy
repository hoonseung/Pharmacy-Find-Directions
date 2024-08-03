package com.backend.finddirections.kakaoapi


import com.backend.finddirections.api.exception.KakaoClientApiException
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
        def ex = thrown(KakaoClientApiException)
        println ex.getHttpStatusCode()
    }


    def "address 파라미터 값이 존재한다면 API 호출에 성공한다"() {
        given:
        String address = "전북 삼성동 100"

        when:
        def result = kakaoAddressSearchService.requestAddressSearch(address)

        then:
        result.meta() != null
        result.meta().totalCount() > 0
        result.documents().size() > 0
        result.documents().get(0) != null
    }





}
