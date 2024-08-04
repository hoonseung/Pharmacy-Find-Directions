package com.backend.finddirections.pharmacy

import com.backend.finddirections.pharmacy.cache.PharmacyRedisTemplateService
import com.backend.finddirections.pharmacy.entity.Pharmacy
import com.backend.finddirections.pharmacy.repository.PharmacyRepository
import com.backend.finddirections.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class PharmacySearchServiceTest extends Specification {

    private PharmacySearchService pharmacySearchService

    private final PharmacyRepository pharmacyRepository = Mock()
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService = Mock()

    List<Pharmacy> pharmacyList

    def setup() {
        pharmacySearchService = new PharmacySearchService(pharmacyRepository, pharmacyRedisTemplateService)

        pharmacyList = new ArrayList<>();
        pharmacyList.addAll(
                Pharmacy.create("호수온누리약국", "주소1", 37.60894036, 127.029052),
                Pharmacy.create("돌곶이온누리약국", "주소2", 37.61040424, 127.0569046)
        )
    }


    def "레디스 장애 시 DB에서 조회가 되는지 검증"() {
        when:
        pharmacyRedisTemplateService.findAll() >> []
        pharmacyRepository.findAll() >> pharmacyList

        def results = pharmacySearchService.searchPharmacyResList()


        then:
        results.size() == 2
        results.getFirst().pharmacyName() == "호수온누리약국"
    }

}