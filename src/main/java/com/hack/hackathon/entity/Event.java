package com.hack.hackathon.entity;

import com.hack.hackathon.enumeration.EventType;

import java.time.LocalDateTime;

public class Event
        extends WithId {
    private String name;
    private EventType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}
