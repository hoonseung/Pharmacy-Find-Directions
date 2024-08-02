package com.backend.finddirections.entity.pharmacy


import org.springframework.beans.factory.annotation.Autowired

class PharmacyRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private PharmacyRepository pharmacyRepository;

    def setup() {
        pharmacyRepository.deleteAll()
    }


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


    def "PharmacyRepository saveAll"() {
        given:
        String name = "은혜 약국"
        String address = "서울 특별시 성북구 종암동"
        Double latitude = 36.11
        Double longitude = 128.11

        when:
        pharmacyRepository.saveAll(List.of(Pharmacy.create(name, address, latitude, longitude)))
        def findResults = pharmacyRepository.findAll()


        then:
        findResults.size() == 1
    }
}
