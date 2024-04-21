package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.RouteDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RoutingService {
    private final PeterburgConfig peterburgConfig;
    private final RestTemplate restTemplate;
    public List<RouteDto> route(Double startX, Double startY, Double endX, Double endY) {
        ResponseEntity<JSONObject> response = restTemplate.getForEntity(peterburgConfig.getRoutingUrl(), JSONObject.class,
                Map.of(
                        "startLong", startX,
                        "startLang", startY,
                        "endLong", endX,
                        "endLang", endY
                ));
        JSONArray body = response.getBody()
                .getJSONArray("features").getJSONObject(0).getJSONObject("geometry")
                .getJSONArray("coordinates");
        var result = new ArrayList<RouteDto>();
        for (int i = 0; i < body.length(); i++) {
            var list = body.getJSONArray(i);
            result.add(new RouteDto(list.getDouble(0), list.getDouble(1)));
        }
        return result;
    }
}
