package com.hack.hackathon.repository;

import com.hack.hackathon.entity.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepository
        extends CrudRepository<Event, Long> {
}
