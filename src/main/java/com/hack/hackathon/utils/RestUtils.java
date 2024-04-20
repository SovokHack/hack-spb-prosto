package com.hack.hackathon.utils;

import com.hack.hackathon.config.PeterburgConfig;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class RestUtils {
    private final PeterburgConfig peterburgConfig;

    public RestUtils(PeterburgConfig peterburgConfig) {
        this.peterburgConfig = peterburgConfig;
    }

    public HttpHeaders createHeaders(){
        return new HttpHeaders() {{
            String authHeader = "Bearer " + peterburgConfig.getApikey();
            set( "Authorization", authHeader );
        }};
    }
}
