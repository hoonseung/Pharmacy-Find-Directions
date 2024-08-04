package com.backend.finddirections.web;


import com.backend.finddirections.dto.Input;
import com.backend.finddirections.dto.Output;
import com.backend.finddirections.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String main() {
        return "main";
    }


    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute(name = "input") Input input) {
        ModelAndView modelAndView = new ModelAndView();
        List<Output> outputs = pharmacyRecommendationService.recommendationPharmacyList(input.getAddress());
        modelAndView.setViewName("output");
        modelAndView.addObject("outputs", outputs);
        return modelAndView;
    }
}
