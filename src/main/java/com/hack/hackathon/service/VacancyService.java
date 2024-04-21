package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.VacancyExperience;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class VacancyService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public JSONArray getExperience(){
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getVacancyUrl(), String.class);
        JSONObject responseObject = new JSONArray(response.getBody()).getJSONObject(0);
        JSONArray experiencesArray = responseObject.getJSONArray("results");
        return experiencesArray;
        }


}
