package com.hack.hackathon.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PeterburgConfig {
    //TODO properties
    private final String apikey="eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJhU1RaZm42bHpTdURYcUttRkg1SzN5UDFhT0FxUkhTNm9OendMUExaTXhFIn0.eyJleHAiOjE4MDgzMDk3MTcsImlhdCI6MTcxMzYxNTMxNywianRpIjoiMjcxMzVjZGItYWNhZC00MGFmLThlMTMtMmIzNTcyOTg2M2NjIiwiaXNzIjoiaHR0cHM6Ly9rYy5wZXRlcnNidXJnLnJ1L3JlYWxtcy9lZ3MtYXBpIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjA0YjZhMmFhLWE1NTktNGI0MC1iOWJmLTQxNTFmYWM1NTBjZiIsInR5cCI6IkJlYXJlciIsImF6cCI6ImFkbWluLXJlc3QtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImJlNjBmYTRiLTI4ZDMtNDliNC1hNTJmLWVmYjJmMmE2N2U2MCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbImRlZmF1bHQtcm9sZXMtZWdzLWFwaSIsIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiJiZTYwZmE0Yi0yOGQzLTQ5YjQtYTUyZi1lZmIyZjJhNjdlNjAiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiLQodC10YDQs9C10Lkg0KDRg9C00L7QsiIsInByZWZlcnJlZF91c2VybmFtZSI6IjQzM2IzOGIxMzVlNWJkN2Y5YWJjZWU1NWU2YTFhZDMwIiwiZ2l2ZW5fbmFtZSI6ItCh0LXRgNCz0LXQuSIsImZhbWlseV9uYW1lIjoi0KDRg9C00L7QsiJ9.YNO9x2VUuubNctAnHyZY7QnHoPH-QKreiKF58sp6h9qQpYCqtqTtAy7E6TkeJfAVpSauVTBLxqHjEYvKAEXFb4hDGnBSNUJMlwTYZmDw9TZvyiFUeA3IJS05kmbbB4jOxxTnnvUS4CBc5LRln2e4Wyq6dvwVGH-6V2Dsq7oIycLbu9uTWF-_-LiIWT8kWY87cnw1pOceaqIT6Sdo4rpJJWzciMQRNmOqCofDddjNLmUaXT_h-P3tfMt0ktDzjnlm-0t46Vcw4UO9g7nlC4CQsZSxO0urWOX_0pnVhWVaQndPH5MxJaUUACZEzjWGbkM297pRLG8V4SRZrhMmq7-hQw";
    private final String mainUrlEvents = "https://science.gate.petersburg.ru/api/v1/public/event/";
    private final String allEventsUrl = mainUrlEvents + "?periodsAfter={periodAfter}";
    private final String eventUrl = mainUrlEvents + "{id}";
    private final String groupsUrl = "https://digital.etu.ru/api/mobile/groups?studyYears=2021-2022";
    private final String coordsUrl = "https://geocode.gate.petersburg.ru/autocomplete/universal?s={query}";
    private final String scheduleUrl = "https://digital.etu.ru/api/mobile/schedule?groupNumber={group}";
    private final String vacanciesUrl = "https://researchinspb.ru/api/v1/public/vacancy/";
    private final String wifiUrl = "https://spb-classif.gate.petersburg.ru/api/v2/datasets/195/versions/latest/data/417/?page={page}&per_page={size}";
    private final String routingUrl = "https://api.openrouteservice.org/v2/directions/foot-walking?api_key=5b3ce3597851110001cf62481d98a47c02b0497ba1dc0d0e709ac442&start={startLong},{startLang}&end={endLong},{endLang}";
}
