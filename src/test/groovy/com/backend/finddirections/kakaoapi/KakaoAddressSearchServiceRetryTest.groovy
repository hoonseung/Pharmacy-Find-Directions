package com.backend.finddirections.kakaoapi

import com.backend.finddirections.api.dto.Document
import com.backend.finddirections.api.dto.KakaoApiResponse
import com.backend.finddirections.api.dto.Meta
import com.backend.finddirections.api.service.KakaoAddressSearchService
import com.backend.finddirections.api.service.KakaoUriBuildService
import com.backend.finddirections.pharmacy.AbstractIntegrationContainerBaseTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest {


    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    @SpringBean
    private KakaoUriBuildService kakaoUriBuildService = Mock()

    private MockWebServer mockWebServer

    private String inputAddress = "서울 성북구 종암로10길"

    private ObjectMapper objectMapper = new ObjectMapper()


    def setup() {
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println mockWebServer.port
        println mockWebServer.url("/")
    }

    def cleanup() {
        mockWebServer.shutdown()
    }


    def "requestAddressSearch retry success"() {
        given:
        def meta = new Meta(1)
        def document = Document.builder()
                .addressName(inputAddress)
                .build()

        def expectedResponse = new KakaoApiResponse(meta, Arrays.asList(document))
        def uri = mockWebServer.url("/").uri()


        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setBody(objectMapper.writeValueAsString(expectedResponse))

        )

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)
        println result

        then:
        2 * kakaoUriBuildService.kakaoAddressUriBuild(inputAddress) >> uri // 2번 호출 확인
        result.getDocuments().size() == 1
        result.getMeta().totalCount() == 1
        result.getDocuments().getFirst().addressName() == inputAddress
    }


    def "requestAddressSearch retry failed"() {
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuildService.kakaoAddressUriBuild(inputAddress) >> uri
        result == null
    }
}