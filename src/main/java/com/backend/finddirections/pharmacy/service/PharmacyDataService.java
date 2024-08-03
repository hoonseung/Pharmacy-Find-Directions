package com.backend.finddirections.pharmacy.service;

import com.backend.finddirections.pharmacy.entity.Pharmacy;
import com.backend.finddirections.pharmacy.repository.PharmacyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PharmacyDataService {

    private final PharmacyRepository pharmacyRepository;


    public List<Pharmacy> findPharmacyAll() {
        return pharmacyRepository.findAll();
    }
}
