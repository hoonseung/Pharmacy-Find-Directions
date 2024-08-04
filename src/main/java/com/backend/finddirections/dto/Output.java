package com.backend.finddirections.dto;

import com.backend.finddirections.direction.entity.Direction;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;


@Slf4j
@Builder
public record Output(
        String pharmacyName,  // 약국명
        String pharmacyAddress, // 약국주소
        String directionUrlPathVariable
        , // 길안내 url 붙일 변수
        String roadViewUrlPathVariable
        , // 로드뷰 url 붙일 변수
        String distance // 고객 검색한 주소와 약국 주소의 거리
) {


    public static Output toDto(Direction direction) {
        String directionComponent = getDirectionPathVariable(direction);

        String roadViewComponent = getRoadViewPathVariable(direction);
        log.info("pathVariable : {}", directionComponent);

        return new Output(
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                directionComponent,
                roadViewComponent,
                String.format("%.2f km", direction.getDistance())
        );
    }

    private static String getDirectionPathVariable(Direction direction) {

        String encoded = new String(direction.getTargetPharmacyName().getBytes(), StandardCharsets.UTF_8);

        return String.join(",",
                encoded,
                String.valueOf(direction.getTargetLatitude()),
                String.valueOf(direction.getTargetLongitude()));
    }

    private static @NotNull String getRoadViewPathVariable(Direction direction) {
        return String.join(",",
                String.valueOf(direction.getTargetLatitude()),
                String.valueOf(direction.getTargetLongitude()));
    }
}
