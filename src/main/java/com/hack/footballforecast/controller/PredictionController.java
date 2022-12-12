package com.hack.footballforecast.controller;


import com.hack.footballforecast.dto.PredictionResponse;
import com.hack.footballforecast.dto.dates.GetDatesValue;
import com.hack.footballforecast.service.PredictionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("predict")
@RequiredArgsConstructor
public class PredictionController {

    private final PredictionService predictionService;

    @GetMapping("/football/{rounds}")
    public List<PredictionResponse> getPrediction(@PathVariable List<String> rounds) {
        return predictionService.getPrediction(rounds);
    }

    @GetMapping("/teams")
    public List<String> getPrediction() {
        return predictionService.getTeamsList();
    }

    @GetMapping("/matches")
    public Map<String, List<GetDatesValue>> getDates() {
        return predictionService.getDates();
    }
}
