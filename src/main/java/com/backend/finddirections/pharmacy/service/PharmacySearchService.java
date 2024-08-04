package com.backend.finddirections.pharmacy.service;


import com.backend.finddirections.pharmacy.cache.PharmacyRedisTemplateService;
import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes;
import com.backend.finddirections.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PharmacySearchService {


    private final PharmacyRepository pharmacyRepository;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    public List<PharmacyRes> searchPharmacyResList() {

        List<PharmacyRes> fromRedis = pharmacyRedisTemplateService.findAll();
        if (!fromRedis.isEmpty()) {
            return fromRedis;
        }

        return pharmacyRepository.findAll()
                .stream()
                .map(PharmacyRes::toDto)
                .toList();
    }
}

