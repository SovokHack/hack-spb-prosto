package com.hack.hackathon.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EventType {
    REMOTE("REMOTE"),
    NOT_REMOTE("NOT_REMOTE"),
    EXTERNAL("EXTERNAL");
    private final String type;
}