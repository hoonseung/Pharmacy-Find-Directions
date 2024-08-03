package com.backend.finddirections.pharmacy.service;


import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class PharmacySearchService {

    private final PharmacyDataService pharmacyService;


    public List<PharmacyRes> searchPharmacyResList(){
        return pharmacyService.findPharmacyAll()
                .stream()
                .map(PharmacyRes::toDto)
                .toList();
    }
}
