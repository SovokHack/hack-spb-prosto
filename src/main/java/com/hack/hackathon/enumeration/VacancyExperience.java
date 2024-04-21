package com.hack.hackathon.enumeration;

public enum VacancyExperience {
    WITHOUT_EXPERIENCE(1, "Без опыта"),
    BEETWEEN_1_3(2, "От 1 года до 3 лет"),
    OVER_6(3, "Более 6 лет"),
    BEETWEEN_3_6(4, "От 3 до 6 лет");

    public int getId() {
        return id;
    }

    public String getViewName() {
        return viewName;
    }

    private int id;
    private String viewName;


    VacancyExperience(int id, String viewName) {
        this.id = id;
        this.viewName = viewName;
    }
}
