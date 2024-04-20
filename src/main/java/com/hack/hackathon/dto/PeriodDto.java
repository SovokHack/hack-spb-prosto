package com.hack.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class PeriodDto {
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone="Europe/Moscow")
    private LocalDateTime lower;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone="Europe/Moscow")
    private LocalDateTime upper;
}
