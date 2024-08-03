package com.backend.finddirections.pharmacy.entity.dto;

import com.backend.finddirections.pharmacy.entity.Pharmacy;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PharmacyRes(
        Long id,
        String pharmacyName,
        String pharmacyAddress,
        Double latitude,
        Double longitude,
        LocalDateTime createdDate

) {

    public static PharmacyRes toDto(Pharmacy pharmacy) {
        return new PharmacyRes(
                pharmacy.getId(),
                pharmacy.getPharmacyName(),
                pharmacy.getPharmacyAddress(),
                pharmacy.getLatitude(),
                pharmacy.getLongitude(),
                pharmacy.getCreatedDate()
        );
    }


}
