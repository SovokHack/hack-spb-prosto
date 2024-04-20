package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.ExternalDto;
import com.hack.hackathon.dto.ExternalEventDto;
import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import com.hack.hackathon.security.SecurityService;
import com.hack.hackathon.util.RestUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class EventExternalService {
    private final RestTemplate restTemplate;
    private final PeterburgConfig peterburgConfig;
    private final RestUtils restUtils;
    private final EventService eventService;

    public List<ExternalEventDto> fetchEvents(Long page, Long size, LocalDateTime periodAfter, LocalDateTime periodBefore) throws IOException {
        ResponseEntity<ExternalDto> responseEntity = restTemplate.exchange(peterburgConfig.getAllEventsUrl(),  HttpMethod.GET, new HttpEntity<>(new ExternalDto()), ExternalDto.class, Map.of(
                "page", page,
                "size", size,
                "periodAfter", periodAfter,
                "periodBefore", periodBefore
        ));
        log.info("res {}", responseEntity.getBody());
        return Objects.requireNonNull(responseEntity.getBody()).getResults();
    }

    public ExternalEventDto eventById(Long eventId) {
        ResponseEntity<ExternalEventDto> responseEntity = restTemplate.exchange(peterburgConfig.getEventUrl(), HttpMethod.GET, new HttpEntity<>(new ExternalEventDto()), ExternalEventDto.class, Map.of(
                "id", eventId
        ));
        return responseEntity.getBody();
    }

    public List<Event> retrieveSchedule(String group, LocalDateTime now) {
        LocalDateTime startTimeOrig = LocalDateTime.of(now.toLocalDate(), LocalTime.of(0, 0));
        LocalDateTime endTimeOrig = LocalDateTime.of(now.toLocalDate(), LocalTime.of(23, 59));
        if (eventService.exists(startTimeOrig, endTimeOrig , Set.of(EventType.REMOTE, EventType.NOT_REMOTE))) {
            ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getScheduleUrl(), String.class, Map.of("group", group));
            Optional<JSONArray> lessons = Optional.ofNullable(new JSONArray(response.getBody())
                    .getJSONObject(0)
                    .getJSONObject(group)
                    .getJSONArray("days")
                    .getJSONObject(now.get(ChronoField.DAY_OF_WEEK))
                    .optJSONArray("lessons"));
            lessons.ifPresent(it -> {
                for (int i = 0; i < it.length(); i++) {
                    JSONObject lesson = it.getJSONObject(i);
                    if (Integer.parseInt(lesson.getString("week")) == ((now.get(ChronoField.ALIGNED_WEEK_OF_YEAR) % 2) + 1)) {
                        var formatter = DateTimeFormatter.ofPattern("HH:mm");
                        var startTime = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(lesson.getString("start_time"), formatter));
                        var endTime = LocalDateTime.of(now.toLocalDate(), LocalTime.parse(lesson.getString("end_time"), formatter));
                        var event = Event.builder().name(lesson.optString("name"))
                                .startTime(startTime)
                                .endTime(endTime)
                                .type(lesson.getBoolean("is_distant") ? EventType.REMOTE : EventType.NOT_REMOTE).build();
                        eventService.save(event);
                    }

                }
            });

        }
        return eventService.findAll(startTimeOrig, endTimeOrig, Set.of(EventType.REMOTE, EventType.NOT_REMOTE));
    }

}
