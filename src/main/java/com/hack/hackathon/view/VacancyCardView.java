package com.hack.hackathon.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;


public class VacancyCardView extends VerticalLayout {
    private final JSONObject json;

    public VacancyCardView(JSONObject json) {
        this.json = json;
        //json.getJSONObject("name").getString("")
        //Имя должности
        TextField jobName = new TextField(json.getString("name"));
        jobName.setReadOnly(true);
        jobName.setLabel("Название должности");

        //Оисание должности
        TextField jobDescription = new TextField(json.getJSONObject("organization").getString("description"));
        jobDescription.setReadOnly(true);
        jobDescription.setLabel("Оисание должности");

        //Название компании
        TextField companyName = new TextField(json.getJSONObject("organization").getString("name"));
        companyName.setReadOnly(true);
        companyName.setLabel("Название компании");

        //Тип занятости
        TextField employmentType = new TextField(json.getJSONObject("employmentType").getString("name"));
        employmentType.setReadOnly(true);
        employmentType.setLabel("Тип занятости");

        //График
        JSONArray scheduleArray = json.getJSONArray("schedule");
        String scheduleName;
        if (scheduleArray.length() > 1) {
            JSONObject secondItem = scheduleArray.getJSONObject(1);
            scheduleName = secondItem.getString("name");
        } else {
            scheduleName = "No data"; // Или другое значение по умолчанию
        }

        TextField scheduleField = new TextField(scheduleName);
        scheduleField.setLabel("График");
        scheduleField.setReadOnly(true);


        //Требуемый опыт
        TextField experience = new TextField(json.getJSONObject("experience").getString("name"));
        experience.setReadOnly(true);
        experience.setLabel("Требуемый опыт");


        //Требуемый уровень образования
        TextField education = new TextField(json.getJSONObject("educationLevel").getString("name"));
        education.setReadOnly(true);
        education.setLabel("Требуемый уровень образования");
        add(jobName, jobDescription, companyName, employmentType, scheduleField);
        if (!json.isNull("salaryFrom")) {
            //Минимальная з/п
            TextField salaryFrom = new TextField(String.valueOf(json.getInt("salaryFrom")));
            salaryFrom.setReadOnly(true);
            salaryFrom.setLabel("Минимальная з/п");
            add(salaryFrom);
        }

        //Максимальная з/п
        if (!json.isNull("salaryUpTo")) {
            TextField salaryUpTo = new TextField(String.valueOf(json.getInt("salaryUpTo")));
            salaryUpTo.setReadOnly(true);
            salaryUpTo.setLabel("Максимальная з/п");
            add(salaryUpTo);
        }

        //Ссылка на hh.ru
        TextField url = new TextField(json.getString("hhUrl"));
        url.setReadOnly(true);
        url.setLabel("Cсылка на hh.ru");
        add(url);



    }
}
