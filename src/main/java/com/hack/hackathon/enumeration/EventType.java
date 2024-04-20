package com.hack.hackathon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {
    OFFLINE("offline"),
    ONLINE("online"),
    EXTERNAL("external");
    private final String type;
}