package com.hack.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExternalEventDto {
    private String organizerAddress;
    private PeriodDto registrationPeriod;
}
