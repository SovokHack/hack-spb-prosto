package com.hack.hackathon.view;

import com.hack.hackathon.entity.User;

import com.hack.hackathon.layout.MainLayout;
import com.hack.hackathon.security.Role;
import com.hack.hackathon.security.SecurityService;
import com.hack.hackathon.security.UserService;
import com.hack.hackathon.service.GroupService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.auth;
import jakarta.annotation.security.PermitAll;;


@PermitAll
@Route(value = "user", layout = MainLayout.class)
public class UserView extends VerticalLayout {
    private final UserService userService;
    private final GroupService groupService;
    private final SecurityService securityService;


    public UserView(UserService userService, GroupService groupService, SecurityService securityService) {
        this.userService = userService;
        this.groupService = groupService;
        this.securityService = securityService;

        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.setBean(securityService.getAuthenticatedUser());

        TextField username = new TextField("Имя пользователя");
        username.setReadOnly(true);
        TextField homeAddress = new TextField("Домашний адрес");

        ComboBox<String> group = new ComboBox<>("Группа");
        group.setItems(groupService.getGroups());





        binder.bind(username, "username");

        binder.bind(homeAddress, "homeAddress");
        binder.withValidator(user -> groupService.groupExists(user.getGroup()),"Такой группы нет!").bind(group, "group");



        Button confirmButton = new Button("Сохранить изменения");

        username.setWidth("600px");
        homeAddress.setWidth("600px");
        group.setWidth("600px");
        confirmButton.setWidth("600px");

        confirmButton.addClickListener(event -> {
            binder.validate();
            if (binder.isValid()) {
                userService.updateUser(securityService.getAuthenticatedUser().getUsername(), binder.getBean());
                Notification.show("Данные усешно обновлены");
            }
        });


        // Устанавливаем отступы между элементами формы
        setSpacing(true);

        // Устанавливаем выравнивание элементов по центру
        setAlignItems(Alignment.CENTER);

        // Добавляем элементы на форму
        add(
                username,
                homeAddress,
                group,
                confirmButton
        );

        // Устанавливаем размеры формы
        setWidth("100%");

        setHeightFull();
    }
}

