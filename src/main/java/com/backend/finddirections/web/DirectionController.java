package com.backend.finddirections.web;

import com.backend.finddirections.direction.entity.Direction;
import com.backend.finddirections.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;


@RequiredArgsConstructor
@Controller
public class DirectionController {

    private final DirectionService directionService;
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";


    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable(name = "encodedId") String encodedId) {
        Direction direction = directionService.findById(encodedId);
        String param = String.join(",", direction.getTargetPharmacyName(), String.valueOf(direction.getTargetLatitude()),
                String.valueOf(direction.getTargetLongitude()));

        String uriString = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + param).toUriString();


        return "redirect:" + uriString;
    }

}
