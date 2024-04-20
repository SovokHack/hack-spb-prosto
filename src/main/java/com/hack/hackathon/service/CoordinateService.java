package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.entity.Coordinate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CoordinateService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public List<Coordinate> query(String query) {
        ResponseEntity<Coordinate[]> responseEntity = restTemplate.getForEntity(peterburgConfig.getCoordsUrl(), Coordinate[].class, Map.of("query", query) );
        return List.of(responseEntity.getBody());

    }
}
