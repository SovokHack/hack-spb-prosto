package com.hack.hackathon.repository;

import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventRepository
        extends JpaRepository<Event, Long> {
    boolean existsEventByStartTimeAfterAndEndTimeBeforeAndTypeIn(LocalDateTime startTime, LocalDateTime endTime, Collection<EventType> type);
    List<Event> findAllByStartTimeAfterAndEndTimeBeforeAndTypeIn(LocalDateTime startTime, LocalDateTime endTime, Collection<EventType> type);
}
