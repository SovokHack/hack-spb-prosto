package com.hack.hackathon.entity;

import com.hack.hackathon.enumeration.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String externalId;
    private String link;
    private LocalDateTime originalStartTime;
    private LocalDateTime originalEndTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
