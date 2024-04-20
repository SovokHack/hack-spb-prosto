package com.hack.hackathon.view

import com.hack.hackathon.entity.User
import com.hack.hackathon.security.Role
import com.hack.hackathon.security.UserService
import com.hack.hackathon.service.GroupService
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed

@PageTitle("Registration")
@Route("reg")
@AnonymousAllowed
class RegisterPage(
    private val userService: UserService,
    private val groupService: GroupService,
) : VerticalLayout() {
    val user = User(Role.STUDENT, "", "", "")
    init {
        val usernameField = TextField("username") //TODO props
        val passwordField = PasswordField("password")
        val groupField = ComboBox<String>()
        groupField.setItems(groupService.groups)
        val form = FormLayout()
        val binder = Binder(User::class.java)
        binder.bean = user
    }
}