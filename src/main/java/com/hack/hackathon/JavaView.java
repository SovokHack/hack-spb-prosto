package com.hack.hackathon;

import com.hack.hackathon.layout.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route(value = "java", layout = MainLayout.class)
public class JavaView extends Div {
    {
        add(new Span("Hello from Java"));
    }

}
