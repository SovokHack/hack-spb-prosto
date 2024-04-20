package com.hack.hackathon.entity;

import com.hack.hackathon.enumeration.EventType;
import com.sun.jna.platform.win32.Wincon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Event {
    @Id
    private Long id;
    private String name;
    private String description;
    private EventType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @Embedded
    private Coordinate coordinate;
    private String externalId;
    private String link;
    private LocalDateTime originalStartTime;
    private LocalDateTime originalEndTime;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
}
