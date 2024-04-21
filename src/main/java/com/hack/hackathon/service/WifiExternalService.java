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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WifiExternalService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;

    public WiFiDtoList getWifiInfo(int page, int size) {
        ResponseEntity<WiFiDtoList> response = restTemplate.getForEntity(peterburgConfig.getWifiUrl(), WiFiDtoList.class,
                Map.of("page", page,
                        "size", size)
        );
        return response.getBody();
    }

    public Stream<WifiDto> fetchAll() {
        var response = getWifiInfo(1, 100);
        var list = response.getResults().stream();
        for (int i = 2; response.getNext() != null; i ++) {
            response = getWifiInfo(i, 100);
            list = Stream.concat(list, response.getResults().stream());
        }
        return list;
    }

}
