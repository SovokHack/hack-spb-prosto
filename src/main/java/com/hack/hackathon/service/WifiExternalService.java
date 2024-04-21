package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.WiFiDtoList;
import com.hack.hackathon.dto.WifiDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WifiExternalService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public List<WifiDto> getWifiInfo(int page, int size) {
        ResponseEntity<WiFiDtoList> response = restTemplate.getForEntity(peterburgConfig.getWifiUrl(), WiFiDtoList.class,
                Map.of("page", page,
                        "size", size)
        );
        return response.getBody().getResults();
    }
}
