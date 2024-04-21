package com.hack.hackathon.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WifiDto {
    private String nameWifi;
    private String address;
    private List<Float> coordinates;
}
