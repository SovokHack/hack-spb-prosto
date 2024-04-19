package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route

@PageTitle("App | Main")
@Route("mainView", layout = MainLayout::class)
class MainView : Div() {
    init {
        add(Span("Hello World!"))
    }
}