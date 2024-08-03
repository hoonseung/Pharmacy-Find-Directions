package com.backend.finddirections.direction.entity.dto;

import com.backend.finddirections.direction.entity.Direction;

import java.time.LocalDateTime;

public record DirectionRes(
        Long id,
        String inputAddress,
        Double inputLatitude,
        Double inputLongitude,
        String targetPharmacyAddress,
        String targetAddress,
        Double targetLatitude,
        Double targetLongitude,
        Double distance,
        LocalDateTime createdDate
) {


    public static DirectionRes toDto(Direction direction) {
        return new DirectionRes(
                direction.getId(),
                direction.getInputAddress(),
                direction.getInputLatitude(),
                direction.getInputLongitude(),
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                direction.getTargetLatitude(),
                direction.getInputLongitude(),
                direction.getDistance(),
                direction.getCreatedDate()
        );
    }
}
