package com.hack.hackathon.view;


import com.hack.hackathon.entity.Event;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

public class EventView
        extends VerticalLayout {
    EventView(Event event) {
        H3 name = new H3(event.getName());
        Span interval = new Span(event.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) + " - " + event.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm")));
        Paragraph description = new Paragraph(event.getDescription());

        HorizontalLayout horizontalLayout = new HorizontalLayout(name, interval);

        setWidth("1000px");
        getStyle().set("flex-grow", "1");
        horizontalLayout.setWidthFull();
        setFlexGrow(1.0, horizontalLayout);
        horizontalLayout.addClassName(LumoUtility.Gap.MEDIUM);
        horizontalLayout.setWidth("100%");
        horizontalLayout.setHeight("min-content");
        name.setWidth("max-content");
        interval.setWidth("min-content");
        interval.getElement().getThemeList().add("badge");
        description.setWidth("100%");
        description.getStyle().set("font-size", "var(--lumo-font-size-m)");
        add(horizontalLayout, description);
    }
}
