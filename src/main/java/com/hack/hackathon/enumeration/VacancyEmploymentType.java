package com.hack.hackathon.enumeration;

import lombok.Getter;

@Getter
public enum VacancyEmploymentType {
    FULL_TIME_JOB(1,"Полная занятость"),
    PART_TIME_JOB(2,"Частичная занятость"),
    TEMPORARY_JOB(3,"Проектная/Временная работа"),
    INTERN(4,"Стажировка"),
    VOLUNTEER(5,"Волонтерство");
    private final int id;
    private final String name;

    VacancyEmploymentType(int id, String name) {
        this.id = id;
        this.name = name;
    }


}
