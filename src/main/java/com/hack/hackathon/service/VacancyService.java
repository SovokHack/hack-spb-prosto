package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.enumeration.VacancyEmploymentType;
import com.hack.hackathon.enumeration.VacancyExperience;
import com.hack.hackathon.enumeration.VacancySchedule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class VacancyService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public JSONArray getVacancies(VacancyEmploymentType employmentType, VacancyExperience experience, VacancySchedule schedule) {
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getVacanciesUrl(), String.class,
                Map.of("empType", employmentType.getId(),
                        "experience", experience.getId(),
                        "schedule", schedule.getId()
                ));
        JSONObject responseObject = new JSONObject(response.getBody());
        JSONArray experiencesArray = responseObject.getJSONArray("results");
        return experiencesArray;
    }

    public int count(VacancyEmploymentType employmentType, VacancyExperience experience, VacancySchedule schedule) {
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getVacanciesUrl(), String.class,
                Map.of("empType", employmentType.getId(),
                        "experience", experience.getId(),
                        "schedule", schedule.getId()
                ));
        JSONObject responseObject = new JSONObject(response.getBody());
        return responseObject.getInt("count");
    }


}
