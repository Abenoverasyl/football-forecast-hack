package com.hack.footballforecast.service.impl;

import com.hack.footballforecast.dto.PredictionResponse;
import com.hack.footballforecast.dto.dates.GetDatesValue;
import com.hack.footballforecast.service.PredictionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PredictionServiceImpl implements PredictionService {

    private final RestTemplate restTemplate;

    @SneakyThrows
    @Override
    public List<PredictionResponse> getPrediction(List<String> rounds) {
        List<PredictionResponse> list = new ArrayList<>();

        for (int i = 0; i < rounds.size(); i += 2) {
            double[] res = getPredict(rounds.get(i), rounds.get(i + 1));
            list.add(new PredictionResponse(rounds.get(i), res[0], rounds.get(i + 1), res[1]));
        }
        return list;
    }

    @Override
    public List<String> getTeamsList() {
        ResponseEntity<List<String>> rateResponse =
                restTemplate.exchange("http://gatewaylambda.space:10500/get_teams",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
                        });
        return rateResponse.getBody();
    }

    @Override
    public Map<String, List<GetDatesValue>> getDates() {
        Map<String, String> map = new HashMap<>();
        ResponseEntity<Map<String, List<GetDatesValue>>> rateResponse =
                restTemplate.exchange("http://gatewaylambda.space:10500/get_dates",
                        HttpMethod.GET, null, new ParameterizedTypeReference<Map<String, List<GetDatesValue>>>() {
                        });
        return rateResponse.getBody();
    }

    // just copy-pasted, not refactored :)
    private double[] getPredict(String team1, String team2) throws IOException {
        URL url = new URL("http://gatewaylambda.space:10500/predict/");
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setRequestMethod("POST");
        httpConn.setRequestProperty("accept", "application/json");
        httpConn.setRequestProperty("Content-Type", "application/json");

        httpConn.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConn.getOutputStream());
        writer.write("{\n  \"team1\": {\n    \"name\": \"" + team1 +"\"\n  },\n  \"team2\": {\n    \"name\": \"" + team2 + "\"\n  }\n}");
        writer.flush();
        writer.close();
        httpConn.getOutputStream().close();
        InputStream responseStream = httpConn.getInputStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        response = response.replace("[", "").replace("]", "");
        String arr[] = response.split(",");
        return Arrays.stream(arr)
                .mapToDouble(Double::parseDouble)
                .toArray();
    }
}
