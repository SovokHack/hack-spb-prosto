package com.hack.hackathon.service;

import com.hack.hackathon.config.PeterburgConfig;
import com.hack.hackathon.dto.ExternalDto;
import com.hack.hackathon.dto.ExternalEventDto;
import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
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

    public List<ExternalEventDto> fetchEvents(Long page, Long size, LocalDateTime periodAfter, LocalDateTime periodBefore) {
        ResponseEntity<ExternalDto> responseEntity = restTemplate.getForEntity(peterburgConfig.getAllEventsUrl(), ExternalDto.class, Map.of(
                "page", page,
                "size", size,
                "periodAfter", periodAfter.atOffset(ZoneOffset.ofHours(3)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))/*,
                "periodBefore", periodBefore.atOffset(ZoneOffset.ofHours(3)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"))*/
        ));
        log.info("res {}", responseEntity.getBody());
        return Objects.requireNonNull(responseEntity.getBody()).getResults();
    }

    public ExternalEventDto getById(Long eventId) {
        ResponseEntity<ExternalEventDto> responseEntity = restTemplate.exchange(peterburgConfig.getEventUrl(), HttpMethod.GET, new HttpEntity<>(new ExternalEventDto()), ExternalEventDto.class, Map.of(
                "id", eventId
        ));
        return responseEntity.getBody();
    }

    public List<Event> retrieveSchedule(String group, LocalDateTime now) {
        LocalDateTime startTimeOrig = LocalDateTime.of(now.toLocalDate(), LocalTime.of(0, 0));
        LocalDateTime endTimeOrig = LocalDateTime.of(now.toLocalDate(), LocalTime.of(23, 59));
        if (!eventService.exists(startTimeOrig, endTimeOrig , Set.of(EventType.OFFLINE, EventType.ONLINE))) {
            ResponseEntity<String> response = restTemplate.getForEntity(peterburgConfig.getScheduleUrl(), String.class, Map.of("group", group));
            Optional<JSONArray> lessons = Optional.ofNullable(new JSONObject(response.getBody())
                    .getJSONObject(group)
                    .getJSONObject("days")
                    .getJSONObject(String.valueOf((now.get(ChronoField.DAY_OF_WEEK) - 1)))
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
                                .type(lesson.optBoolean("is_distant", false) ? EventType.ONLINE : EventType.OFFLINE).build();
                        eventService.save(event);
                    }

                }
            });

        }
        return eventService.findAll(startTimeOrig, endTimeOrig, Set.of(EventType.ONLINE, EventType.OFFLINE));
    }

}
