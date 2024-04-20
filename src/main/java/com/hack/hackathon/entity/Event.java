package com.hack.hackathon.entity;

import com.hack.hackathon.enumeration.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    @Id
    private Long id;
    private String name;
    private String description;
    private EventType type;
    private LocalTime startTime;
    private LocalTime endTime;
    private String location;
}
