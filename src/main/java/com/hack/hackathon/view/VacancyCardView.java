package com.hack.hackathon.view;

import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.BodySize;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;


public class VacancyCardView extends VerticalLayout {
    private final JSONObject json;
    public VacancyCardView(JSONObject json) {
        this.json = json;
 
        setAlignItems(Alignment.CENTER);
        setHeight("90%");
        setWidth("100%");
        String textFieldWidth = "80%"; // можно изменить значение по необходимости

        // Создаем текстовые поля
        H3 jobName = new H3(json.getString("name"));

//        TextField jobName = new TextField("Название должности");
//        jobName.setReadOnly(true);
//        jobName.setWidth(textFieldWidth);
//        jobName.setValue(json.getString("name"));

        TextField companyName = new TextField("Название компании");
        companyName.setReadOnly(true);
        companyName.setWidth(textFieldWidth);
        companyName.setValue(json.getJSONObject("organization").getString("name"));

        TextField employmentType = new TextField("Тип занятости");
        employmentType.setReadOnly(true);
        employmentType.setWidth(textFieldWidth);
        employmentType.setValue(json.getJSONObject("employmentType").getString("name"));

        JSONArray scheduleArray = json.getJSONArray("schedule");
        String scheduleName = scheduleArray.length() > 1 ?
                scheduleArray.getJSONObject(1).getString("name") :
                "Не указано"; // или другое значение по умолчанию

        TextField scheduleField = new TextField("График");
        scheduleField.setReadOnly(true);
        scheduleField.setWidth(textFieldWidth);
        scheduleField.setValue(scheduleName);

        TextField experience = new TextField("Требуемый опыт");
        experience.setReadOnly(true);
        experience.setWidth(textFieldWidth);
        experience.setValue(json.getJSONObject("experience").getString("name"));

        TextField education = new TextField("Требуемый уровень образования");
        education.setReadOnly(true);
        education.setWidth(textFieldWidth);
        education.setValue(json.getJSONObject("educationLevel").getString("name"));

        // Добавляем текстовые поля в главный вертикальный лейаут
        add(jobName, companyName, employmentType, scheduleField, experience, education);

        if (!json.isNull("salaryFrom")) {
            TextField salaryFrom = new TextField("Минимальная з/п");
            salaryFrom.setReadOnly(true);
            salaryFrom.setWidth(textFieldWidth);
            salaryFrom.setValue(String.valueOf(json.getInt("salaryFrom")));
            add(salaryFrom);
        }

        if (!json.isNull("salaryUpTo")) {
            TextField salaryUpTo = new TextField("Максимальная з/п");
            salaryUpTo.setReadOnly(true);
            salaryUpTo.setWidth(textFieldWidth);
            salaryUpTo.setValue(String.valueOf(json.getInt("salaryUpTo")));
            add(salaryUpTo);
        }

        TextField url = new TextField("Ссылка на hh.ru");
        url.setReadOnly(true);
        url.setWidth(textFieldWidth);
        url.setValue(json.getString("hhUrl"));
        add(url);

    }

}
