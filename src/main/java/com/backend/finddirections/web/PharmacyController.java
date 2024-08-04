package com.backend.finddirections.web;

import com.backend.finddirections.pharmacy.cache.PharmacyRedisTemplateService;
import com.backend.finddirections.pharmacy.entity.dto.PharmacyRes;
import com.backend.finddirections.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PharmacyController {

    private final PharmacyRepository pharmacyRepository;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;


    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save() {
        List<PharmacyRes> pharmacyResList = pharmacyRepository.findAll()
                .stream()
                .map(PharmacyRes::toDto)
                .toList();

        pharmacyResList.forEach(pharmacyRedisTemplateService::save);

        return "save success";
    }
}
