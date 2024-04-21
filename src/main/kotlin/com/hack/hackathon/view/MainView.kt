package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.H4
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode

@PageTitle("Приложение | Главная")
@Route("mainView", layout = MainLayout::class)
@AnonymousAllowed
class MainView : VerticalLayout() {

    init {

        defaultHorizontalComponentAlignment = Alignment.CENTER
        setJustifyContentMode(JustifyContentMode.CENTER)

        val contentWrapper = Div()
        contentWrapper.setWidth("50%")  // Фиксированная ширина
        contentWrapper.style.set("border", "1px solid #ccc")  // Граница
        contentWrapper.style.set("border-radius", "10px")  // Закругленные углы
        contentWrapper.style.set("padding", "20px")  // Внутренний отступ
        contentWrapper.style.set("box-shadow", "0 4px 10px 0 rgba(0,0,0,0.1)")  // Легкая тень
        contentWrapper.style.set("background", "linear-gradient(to right, #e3f2fd, #bbdefb)")  // Градиент
        contentWrapper.style.set("text-align", "center")  // Центрирование текста
        contentWrapper.style.set("color", "blue")  // Синий цвет текста
        contentWrapper.style.set("margin-top", "20px")  // Отступ сверху
        contentWrapper.style.set("margin-bottom", "20px")  // Отступ снизу


        val header = H4("Добро пожаловать в наше приложение!")
        header.style.set("font-size", "2em")  // Увеличиваем размер шрифта

        val description = Span("Это сервис, который делает жизнь студента немного проще)")
        description.style.set("font-size", "1.2em")  // Увеличиваем размер шрифта

        contentWrapper.add(header, description)

        add(contentWrapper)

        setSpacing(true)
    }
}

