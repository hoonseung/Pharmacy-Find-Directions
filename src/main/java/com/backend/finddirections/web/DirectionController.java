package com.backend.finddirections.web;

import com.backend.finddirections.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequiredArgsConstructor
@Controller
public class DirectionController {

    private final DirectionService directionService;


    @GetMapping("/dir/{encodedId}")
    public String searchDirection(@PathVariable(name = "encodedId") String encodedId) {
        String directionUrl = directionService.getDirectionUrlByEncodedId(encodedId);
        return "redirect:" + directionUrl;
    }

}
