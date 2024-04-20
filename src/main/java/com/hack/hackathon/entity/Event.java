package com.hack.hackathon.entity;

import com.hack.hackathon.enumeration.EventType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {
    @Id
    private Long id;
    private String name;
    private EventType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String location;
}
