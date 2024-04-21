package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.hack.hackathon.service.RoutingService
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import jakarta.annotation.security.PermitAll

@PageTitle("App | Main")
@Route("mainView", layout = MainLayout::class)
@AnonymousAllowed
class MainView(
    private val routingService: RoutingService
) : Div() {
    init {
        val r = routingService.route(34.45435,24.6546,54.654,23.45)
        add(Span("Hello World!"))

    }
}