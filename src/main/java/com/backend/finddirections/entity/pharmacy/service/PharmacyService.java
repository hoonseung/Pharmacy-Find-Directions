package com.backend.finddirections.entity.pharmacy.service;

import com.backend.finddirections.api.service.KakaoAddressSearchService;
import com.backend.finddirections.entity.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PharmacyService {

    private final PharmacyRepository pharmacyRepository;
    private final KakaoAddressSearchService kakaoAddressSearchService;



}
