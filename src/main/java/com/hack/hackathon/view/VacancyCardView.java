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
         setWidthFull();
        //Имя должности
        TextField jobName = new TextField("Имя должности");
        jobName.setReadOnly(true);
        jobName.setValue(json.getString("name"));


        //Название компании
        TextField companyName = new TextField("Название компании");
        companyName.setReadOnly(true);
        companyName.setValue(json.getJSONObject("organization").getString("name"));

        //Тип занятости
        TextField employmentType = new TextField("Тип занятости");
        employmentType.setReadOnly(true);
        employmentType.setValue(json.getJSONObject("employmentType").getString("name"));

        //График
        JSONArray scheduleArray = json.getJSONArray("schedule");
        String scheduleName;
        if (scheduleArray.length() > 1) {
            JSONObject secondItem = scheduleArray.getJSONObject(1);
            scheduleName = secondItem.getString("name");
        } else {
            scheduleName = "No data"; // Или другое значение по умолчанию
        }

        TextField scheduleField = new TextField("График");
        scheduleField.setValue(scheduleName);
        scheduleField.setReadOnly(true);


        //Требуемый опыт
        TextField experience = new TextField("Требуемый опыт");
        experience.setReadOnly(true);
        experience.setValue(json.getJSONObject("experience").getString("name"));


        //Требуемый уровень образования
        TextField education = new TextField("Требуемый уровень образования");
        education.setReadOnly(true);
        education.setValue(json.getJSONObject("educationLevel").getString("name"));
        add(jobName, companyName, employmentType, scheduleField);

        if (!json.isNull("salaryFrom")) {
            //Минимальная з/п
            TextField salaryFrom = new TextField("Минимальная з/п");
            salaryFrom.setReadOnly(true);
            salaryFrom.setValue(String.valueOf(json.getInt("salaryFrom")));
            add(salaryFrom);
        }

        //Максимальная з/п
        if (!json.isNull("Максимальная з/п")) {
            TextField salaryUpTo = new TextField();
            salaryUpTo.setReadOnly(true);
            salaryUpTo.setValue(String.valueOf(json.getInt("salaryUpTo")));
            add(salaryUpTo);
        }

        //Ссылка на hh.ru
        TextField url = new TextField("Ссылка на hh.ru");
        url.setReadOnly(true);
        url.setValue(json.getString("hhUrl"));
        add(url);

    }

}
