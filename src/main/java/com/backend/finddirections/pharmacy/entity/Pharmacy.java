package com.backend.finddirections.pharmacy.entity;


import com.backend.finddirections.BaseTemporalEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Pharmacy extends BaseTemporalEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmacy_id")
    private Long id;

    @Column
    private String pharmacyName;

    @Column
    private String pharmacyAddress;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;


    private Pharmacy(String pharmacyName, String pharmacyAddress, Double latitude, Double longitude) {
        this.pharmacyName = pharmacyName;
        this.pharmacyAddress = pharmacyAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Pharmacy create(String pharmacyName, String pharmacyAddress, Double latitude, Double longitude) {
        return new Pharmacy(pharmacyName, pharmacyAddress, latitude, longitude);
    }
}
