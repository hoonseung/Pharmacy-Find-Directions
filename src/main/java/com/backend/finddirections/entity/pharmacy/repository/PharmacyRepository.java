package com.backend.finddirections.entity.pharmacy.repository;

import com.backend.finddirections.entity.pharmacy.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
