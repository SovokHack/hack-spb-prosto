package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.ExternalDto;
import com.hack.hackathon.dto.ExternalEventDto;
import com.hack.hackathon.util.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Service
@AllArgsConstructor
@Slf4j
public class EventExternalService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;
    private final RestUtils restUtils;

    public List<ExternalEventDto> fetchEvents(Long page, Long size, LocalDateTime periodAfter, LocalDateTime periodBefore) throws IOException {
        ResponseEntity<ExternalDto> responseEntity = restTemplate.exchange(peterburgConfig.getAllEventsUrl(),  HttpMethod.GET, new HttpEntity<>(new ExternalDto()), ExternalDto.class, Map.of(
                "page", page,
                "size", size,
                "periodAfter", periodAfter,
                "periodBefore", periodBefore
        ));
        log.info("res {}", responseEntity.getBody());
        return Objects.requireNonNull(responseEntity.getBody()).getResults();
    }

    public ExternalEventDto eventById(Long eventId) {
        ResponseEntity<ExternalEventDto> responseEntity = restTemplate.exchange(peterburgConfig.getEventUrl(), HttpMethod.GET, new HttpEntity<>(new ExternalEventDto()), ExternalEventDto.class, Map.of(
                "id", eventId
        ));
        return responseEntity.getBody();
    }

}
