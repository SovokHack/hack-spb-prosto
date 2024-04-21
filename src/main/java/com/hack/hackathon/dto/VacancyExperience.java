package com.hack.hackathon.dto;

import lombok.Getter;


public enum VacancyExperience {
    WITHOUT_EXPERIENCE(1, "Без опыта"),
    BEETWEEN_1_3(2,"От 1 года до 3 лет"),
    OVER_6(3,"Более 6 лет"),
    BEETWEEN_3_6(4, "От 3 до 6 лет");


    private final int id;
    private final String name;


    VacancyExperience(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
