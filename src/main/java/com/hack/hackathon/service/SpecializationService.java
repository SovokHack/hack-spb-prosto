package com.hack.hackathon.service;

import com.hack.hackathon.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpecializationService {
    private final SecurityService securityService;
    private final Map<Integer, String> map = Map.of(
            0, "Программист",
            1, "Разработчик",
            2, "Репетитор",
            3, "Сантехник"
    );

    public String getSpecialization() {
        int group = Integer.parseInt(securityService.getAuthenticatedUser().getGroup());
        return map.get(group % 4);
    }
}
