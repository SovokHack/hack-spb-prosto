package com.hack.hackathon.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.ExternalDto;
import com.hack.hackathon.dto.ExternalEventDto;
import com.hack.hackathon.utils.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@AllArgsConstructor
@Slf4j
public class EventExternalService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;
    private final RestUtils restUtils;

    public List<ExternalEventDto> fetchEvents(Long page, Long size, LocalDateTime periodsAfter, LocalDateTime periodBefore) throws IOException {
        ResponseEntity<ExternalDto> responseEntity = restTemplate.exchange(peterburgConfig.getUrl() + "?page=" + page + "&pagesize=" + size,  HttpMethod.GET, new HttpEntity<>(new ExternalDto()), ExternalDto.class);
        log.info("res {}", responseEntity.getBody());
        return Objects.requireNonNull(responseEntity.getBody()).getResults();
    }

}
