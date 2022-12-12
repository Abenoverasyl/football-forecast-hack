package com.hack.footballforecast.dto.dates;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDatesValue {
    @JsonProperty("Date")
    private String date;
    @JsonProperty("Time")
    private String Time;
    @JsonProperty("Team_1")
    private String Team_1;
    @JsonProperty("Team_2")
    private String Team_2;
    @JsonProperty("Score")
    private String Score;
    @JsonProperty("winnerTeam")
    private String winnerTeam;
    private Object probability;
}
