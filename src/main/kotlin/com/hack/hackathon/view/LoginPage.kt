package com.hack.hackathon.view

import com.hack.hackathon.layout.MainLayout
import com.hack.hackathon.security.SecurityService
import com.hack.hackathon.security.UserService
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.login.LoginForm
import com.vaadin.flow.component.notification.Notification
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.BeforeEnterEvent
import com.vaadin.flow.router.BeforeEnterObserver
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import jakarta.annotation.security.PermitAll


@Route("login", layout = MainLayout::class)
@PageTitle("Login")
@PermitAll
class LoginPage(
    private val userService: UserService,
    private val securityService: SecurityService
) : VerticalLayout(), BeforeEnterObserver {
    private val loginForm = LoginForm()
    init {
            init()
        loginForm.isForgotPasswordButtonVisible = false
    }

    fun init() {
        loginForm.action = "login"
        add(loginForm)
    }

    override fun beforeEnter(beforeEnterEvent: BeforeEnterEvent?) {
        if(beforeEnterEvent?.location?.queryParameters?.parameters?.containsKey("error") == true) {
            loginForm.isError = true;
        }
    }
}