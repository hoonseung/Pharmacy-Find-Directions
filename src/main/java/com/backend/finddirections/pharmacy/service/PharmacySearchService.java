package com.backend.finddirections.pharmacy.service;


import com.backend.finddirections.pharmacy.cache.PharmacyRedisTemplateService;
import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes;
import com.backend.finddirections.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PharmacySearchService {


    private final PharmacyRepository pharmacyRepository;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    public List<PharmacyRes> searchPharmacyResList() {

        List<PharmacyRes> fromRedis = pharmacyRedisTemplateService.findAll();
        if (!fromRedis.isEmpty()) {
            log.info("redis findAll success");
            return fromRedis;
        }

        return pharmacyRepository.findAll()
                .stream()
                .map(PharmacyRes::toDto)
                .toList();
    }
}

