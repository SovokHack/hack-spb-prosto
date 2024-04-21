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
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VacancyService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public JSONArray getVacancies(String search, VacancyEmploymentType employmentType, VacancyExperience experience, VacancySchedule schedule) {
        StringBuilder suffix = new StringBuilder("");
        Optional.ofNullable(employmentType).map(VacancyEmploymentType::getId).ifPresent(it -> suffix.append("&employment_type=").append(it));
        Optional.ofNullable(experience).map(VacancyExperience::getId).ifPresent(it -> suffix.append("&experience=").append(it));
        Optional.ofNullable(schedule).map(VacancySchedule::getId).ifPresent(it -> suffix.append("&schedule=").append(it));
        if (!suffix.isEmpty()) {
            suffix.deleteCharAt(0);
            suffix.replace(0, 0, "?");
        }
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getVacanciesUrl() + suffix, String.class, Optional.ofNullable(employmentType).map(VacancyEmploymentType::getId).orElse(null), Optional.ofNullable(experience).map(VacancyExperience::getId).orElse(null), Optional.ofNullable(schedule).map(VacancySchedule::getId).orElse(null));
        JSONObject responseObject = new JSONObject(response.getBody());
        JSONArray experiencesArray = responseObject.getJSONArray("results");
        return experiencesArray;
    }

    public int count(String search, VacancyEmploymentType employmentType, VacancyExperience experience, VacancySchedule schedule) {
        ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getVacanciesUrl(), String.class,
                Map.of("empType", employmentType.getId(),
                        "experience", experience.getId(),
                        "schedule", schedule.getId(),
                        "search", search
                ));
        JSONObject responseObject = new JSONObject(response.getBody());
        return responseObject.getInt("count");
    }


}
