package com.hack.hackathon.dto;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class ExternalEventDto {
    //tur
    private Integer id;
    //tur
    @JsonProperty("organizerAddress")
    private String organizerAddress;
    private List<PeriodDto> periods;
    @JsonProperty("isAvailable")
    private Boolean isAvailable;
    //tur
    private List<Double> coordinates;
    @JsonProperty("title")
    private String title;


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class PeriodDto {
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone = "Europe/Moscow")
        private LocalDateTime lower;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX", timezone = "Europe/Moscow")
        private LocalDateTime upper;
    }
}


