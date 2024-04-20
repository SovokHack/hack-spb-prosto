package com.hack.hackathon.service;

import com.hack.hackathon.entity.Event;
import com.hack.hackathon.exception.Exceptions;
import com.hack.hackathon.exception.NotFoundException;
import com.hack.hackathon.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class EventService {
    private final EventRepository eventRepository;

    private final ModelMapper modelMapper;

    public Event getById(Long id)
            throws NotFoundException {
        Optional<Event> foundCharacter = eventRepository.findById(id);

        return foundCharacter.orElseThrow(() -> new NotFoundException(Exceptions.EVENT_NOT_FOUND));
    }

    public void existsById(Long id)
            throws NotFoundException {
        eventRepository.findById(id).orElseThrow(() -> new NotFoundException(Exceptions.EVENT_NOT_FOUND));
    }

    public Page<Event> getAll(Pageable pageable){
        return eventRepository.findAll(pageable);
    }

    @Transactional
    public void saveAll(List<Event> eventList){
        eventRepository.saveAll(eventList);
    }

    @Transactional
    public Event create(Event event) {
        eventRepository.save(event);

        log.info("Event with ID {} is created", event);

        return event;
    }

    @Transactional
    public Event update(Event unsavedEvent, Long id) {
        Event event = new Event();
        modelMapper.map(getById(id), event);
        modelMapper.map(unsavedEvent, event);
        eventRepository.save(event);

        log.info("Event with ID {} is updated", event.getId());

        return event;
    }

    @Transactional
    public void delete(Long id)
            throws NotFoundException {
        existsById(id);
        eventRepository.deleteById(id);

        log.info("Event with ID {} is deleted", id);
    }
}
