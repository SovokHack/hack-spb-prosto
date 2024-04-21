package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@Slf4j
public class VacancyScheduleService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public JSONArray getSchedule(){
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getScheduleUrl(), String.class);
        JSONObject responseObject = new JSONArray(response.getBody()).getJSONObject(0);
        JSONArray scheduleArray = responseObject.getJSONArray("results");
        return scheduleArray;
    }

}
