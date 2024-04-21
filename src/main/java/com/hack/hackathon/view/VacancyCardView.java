package com.hack.hackathon.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
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
        TextField schedule = new TextField(json.getJSONObject("schedule").getString("name"));
        schedule.setReadOnly(true);
        schedule.setLabel("График");


        //Требуемый опыт
        TextField experience = new TextField(json.getJSONObject("experience").getString("name"));
        experience.setReadOnly(true);
        experience.setLabel("Требуемый опыт");


        //Требуемый уровень образования
        TextField education = new TextField(json.getJSONObject("educationLevel").getString("name"));
        education.setReadOnly(true);
        education.setLabel("Требуемый уровень образования");

        //Минимальная з/п
        TextField salaryFrom = new TextField(json.getString("salaryFrom"));
        salaryFrom.setReadOnly(true);
        salaryFrom.setLabel("Минимальная з/п");

        //Максимальная з/п
        TextField salaryUpTo = new TextField(json.getString("salaryUpTo"));
        salaryUpTo.setReadOnly(true);
        salaryFrom.setLabel("Максимальная з/п");

        //Ссылка на hh.ru
        TextField url = new TextField(json.getString("hhUrl"));
        url.setReadOnly(true);
        url.setLabel("Cсылка на hh.ru");



    }
}
