package com.hack.hackathon.view;

import com.hack.hackathon.entity.Event;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class EventEditView
        extends VerticalLayout {
    EventEditView(TextField nameField, TimePicker startTime, TimePicker endTime, TextField descriptionField) {
        HorizontalLayout horizontalLayout = new HorizontalLayout(nameField, startTime, endTime);

        setWidth("1000px");
        getStyle().set("flex-grow", "1");
        horizontalLayout.setWidthFull();
        setFlexGrow(1.0, horizontalLayout);
        horizontalLayout.addClassName(LumoUtility.Gap.MEDIUM);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight("min-content");
        nameField.setWidth("max-content");
        startTime.setWidth("150px");
        endTime.setWidth("150px");
        descriptionField.setWidth("100%");
        descriptionField.getStyle().set("font-size", "var(--lumo-font-size-m)");

        add(horizontalLayout, descriptionField);
    }
}
