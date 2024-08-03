package com.backend.finddirections.direction.entity;

import com.backend.finddirections.BaseTemporalEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Direction extends BaseTemporalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "direction_id")
    private Long id;

    private String inputAddress;

    @Column(nullable = false)
    private Double inputLatitude;

    @Column(nullable = false)
    private Double inputLongitude;

    private String targetPharmacyName;

    private String targetAddress;

    @Column(nullable = false)
    private Double targetLatitude;

    @Column(nullable = false)
    private Double targetLongitude;

    @Column(nullable = false)
    private Double distance;

    @Builder
    public Direction(String inputAddress, Double inputLatitude, Double inputLongitude, String targetPharmacyName, String targetAddress, Double targetLatitude, Double targetLongitude, Double distance) {
        this.inputAddress = inputAddress;
        this.inputLatitude = inputLatitude;
        this.inputLongitude = inputLongitude;
        this.targetPharmacyName = targetPharmacyName;
        this.targetAddress = targetAddress;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.distance = distance;
    }

}
