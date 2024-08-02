package com.backend.finddirections.entity.pharmacy

import com.backend.finddirections.AbstractIntegrationContainerBaseTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.DynamicPropertySource

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @DynamicPropertySource

    def "PharmacyRepository save"() {
        given:
        String name = "은혜 약국"
        String address = "서울 특별시 성북구 종암동"
        Double latitude = 36.11
        Double longitude = 128.11


        def pharmacy = Pharmacy.create(name, address, latitude, longitude);

        when:
        def result = pharmacyRepository.save(pharmacy);

        then:
        result.getPharmacyAddress() == address
        result.getPharmacyName() == name
        result.getLatitude() == latitude
        result.getLongitude() == longitude

    }
}
