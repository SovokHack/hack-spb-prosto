package com.hack.hackathon.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class EventExternalService {
    private final RestTemplate restTemplate;
}
