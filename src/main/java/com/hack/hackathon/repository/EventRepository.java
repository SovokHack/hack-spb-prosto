package com.hack.hackathon.repository;

import com.hack.hackathon.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository
        extends JpaRepository<Event, Long> {
}
