package com.hack.footballforecast.service;

import com.hack.footballforecast.dto.PredictionResponse;
import com.hack.footballforecast.dto.dates.GetDatesValue;

import java.util.List;
import java.util.Map;

public interface PredictionService {
    List<PredictionResponse> getPrediction(List<String> rounds);
    List<String> getTeamsList();
    Map<String, List<GetDatesValue>> getDates();
}
