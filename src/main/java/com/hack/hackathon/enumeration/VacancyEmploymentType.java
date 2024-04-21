package com.hack.hackathon.enumeration;

public enum VacancyEmploymentType {
    FULL_TIME_JOB(1,"Полная занятость"),
    PART_TIME_JOB(2,"Частичная занятость"),
    TEMPORARY_JOB(3,"Проектная/Временная работа"),
    INTERN(4,"Стажировка"),
    VOLUNTEER(5,"Волонтерство");
    private int id;
    private String viewName;

    VacancyEmploymentType(int id, String viewName) {
        this.id = id;
        this.viewName = viewName;
    }


    public int getId() {
        return id;
    }

    public String getViewName() {
        return viewName;
    }
}
