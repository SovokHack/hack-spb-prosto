package com.hack.hackathon.view

import com.hack.hackathon.entity.User
import com.hack.hackathon.layout.MainLayout
import com.hack.hackathon.security.Role
import com.hack.hackathon.security.UserService
import com.hack.hackathon.service.GroupService
import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.combobox.ComboBox
import com.vaadin.flow.component.formlayout.FormLayout
import com.vaadin.flow.component.orderedlayout.HorizontalLayout
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.PasswordField
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.data.binder.Binder
import com.vaadin.flow.router.PageTitle
import  com.vaadin.flow.router.Route
import com.vaadin.flow.server.auth.AnonymousAllowed

@PageTitle("Registration")
@Route("reg", layout = MainLayout::class)
@AnonymousAllowed
class RegisterPage(
    private val userService: UserService,
    private val groupService: GroupService,
) : VerticalLayout() {
    val user = User(Role.STUDENT, "", "", "", "")
    init {
        val usernameField = TextField("username") //TODO props
        val passwordField = PasswordField("password")
        val groupField = ComboBox<String>("group")
        groupField.setItems(groupService.groups)
        val homeAddressField = TextField("homeAddress")
        val form = FormLayout()
        val binder = Binder(User::class.java)
        binder.bean = user
        val emptyValidator: (String?) -> Boolean = {gr -> gr?.isNotBlank() ?: true}
        binder.forField(usernameField).asRequired().withValidator(emptyValidator, "must not be empty").withValidator({username -> !userService.usernameExists(username)}, "username already exists").bind({it.username}, {user, username -> user.username = username})
        binder.forField(groupField).asRequired().withValidator(emptyValidator, "must not be empty").withValidator({group -> groupService.groupExists(group)}, "group already exists").bind({it.group },{user, group -> user.group = group})
        binder.forField(passwordField).asRequired().withValidator(emptyValidator, "must not be empty").bind({ it.password }, { user, pass -> user.password = pass } )
        binder.forField(homeAddressField).asRequired().withValidator(emptyValidator, "must not be empty").bind({ it.homeAddress }, { user, address -> user.homeAddress = address })
        form.add(usernameField, passwordField, groupField, homeAddressField)
        val cancelButton = Button("Cancel") {
            UI.getCurrent().navigate(MainView::class.java)
        }
        form.setResponsiveSteps(
            // Use one column by default
            FormLayout.ResponsiveStep("0", 1)
        )
        val submitBtn = Button("Save") {
            if (binder.isValid) {
                userService.registerUser(binder.bean)
                UI.getCurrent().navigate(LoginPage::class.java)
            }
        }
        val horizontal = HorizontalLayout(cancelButton, submitBtn)
        add(form, horizontal)
    }
}