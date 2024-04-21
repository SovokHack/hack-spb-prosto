package com.hack.hackathon.dto;

public enum VacancySchedule {
    FULL_DAY(1,"Полный день"),
    SHIFT_SCHEDULE(2, "Сменный график"),
    FLEXIBLE_SCHEDULE(3, "Гибкий график"),
    DISTANT_JOB(4, "Удаленная работа"),
    WATCH_METHOD(5, "Вахтовый метод")
    ;


    private final int id;

    private final String name;

    VacancySchedule(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
