package com.backend.finddirections.dto;

import com.backend.finddirections.direction.entity.Direction;
import com.backend.finddirections.direction.service.Base62Service;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;


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
        Base62Service base62Service = new Base62Service();
        String encodeDirectionId = base62Service.encodeDirectionId(direction.getId());
        String roadViewComponent = getRoadViewPathVariable(direction);


        return new Output(
                direction.getTargetPharmacyName(),
                direction.getTargetAddress(),
                encodeDirectionId,
                roadViewComponent,
                String.format("%.2f km", direction.getDistance())
        );
    }


    private static String getRoadViewPathVariable(Direction direction) {
        return String.join(",",
                String.valueOf(direction.getTargetLatitude()),
                String.valueOf(direction.getTargetLongitude()));
    }
}
