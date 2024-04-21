package com.hack.hackathon.enumeration;

public enum VacancySchedule {
    FULL_DAY(1,"Полный день"),
    SHIFT_SCHEDULE(2, "Сменный график"),
    FLEXIBLE_SCHEDULE(3, "Гибкий график"),
    DISTANT_JOB(4, "Удаленная работа"),
    WATCH_METHOD(5, "Вахтовый метод")
    ;

    public int getId() {
        return id;
    }

    public String getViewName() {
        return viewName;
    }

    private final int id;

    private final String viewName;

    VacancySchedule(int id, String viewName) {
        this.id = id;
        this.viewName = viewName;
    }
}
