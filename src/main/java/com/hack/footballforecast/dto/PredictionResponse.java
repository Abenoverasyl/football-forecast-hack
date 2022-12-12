package com.hack.footballforecast.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PredictionResponse {
    private String team1Name;
    private Double team1Prediction;
    private String team2Name;
    private Double team2Prediction;
}
