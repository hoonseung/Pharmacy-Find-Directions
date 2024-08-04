package com.backend.finddirections.pharmacy.cache

import com.backend.finddirections.pharmacy.AbstractIntegrationContainerBaseTest
import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes
import org.springframework.beans.factory.annotation.Autowired

class PharmacyRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRedisTemplateService pharmacyRedisTemplateService


    def setup() {
        pharmacyRedisTemplateService.findAll()
                .forEach(data -> pharmacyRedisTemplateService.deleteById(data.id()))
    }


    def "save success"() {
        given:
        def pharmacyRes = PharmacyRes.builder()
                .id(1L)
                .pharmacyName("약국1")
                .pharmacyAddress("주소1")
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyRes)
        def pharmacyResList = pharmacyRedisTemplateService.findAll()

        then:
        pharmacyResList.size() == 1
        pharmacyResList.getFirst().id() == 1L
        pharmacyResList.getFirst().pharmacyName() == "약국1"
    }


    def "id가 없을 때 save fail"() {
        given:
        def pharmacyRes = PharmacyRes.builder()
                .pharmacyName("약국1")
                .pharmacyAddress("주소1")
                .build()

        when:
        pharmacyRedisTemplateService.save(pharmacyRes)
        def pharmacyResList = pharmacyRedisTemplateService.findAll()

        then:
        pharmacyResList.size() == 0

    }
}