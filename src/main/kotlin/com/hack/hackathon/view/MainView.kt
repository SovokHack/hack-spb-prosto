package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import jakarta.annotation.security.PermitAll

@PageTitle("App | Main")
@Route("mainView", layout = MainLayout::class)
@AnonymousAllowed
class MainView : Div() {
    init {
        add(Span("Hello World!"))
    }
}