package com.hack.hackathon.view;

import com.hack.hackathon.entity.Coordinate;
import com.hack.hackathon.entity.User;

import com.hack.hackathon.layout.MainLayout;
import com.hack.hackathon.security.SecurityService;
import com.hack.hackathon.security.UserService;
import com.hack.hackathon.service.CoordinateService;
import com.hack.hackathon.service.GroupService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.Route;
//import com.vaadin.flow.server.auth;
import jakarta.annotation.security.PermitAll;;import java.util.Collections;


@PermitAll
@Route(value = "user", layout = MainLayout.class)
public class UserView extends VerticalLayout {
    private final UserService userService;
    private final GroupService groupService;
    private final SecurityService securityService;

    private final CoordinateService coordinateService;


    public UserView(UserService userService, GroupService groupService, SecurityService securityService, CoordinateService coordinateService) {
        this.userService = userService;
        this.groupService = groupService;
        this.securityService = securityService;
        this.coordinateService = coordinateService;


        BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
        binder.setBean(securityService.getAuthenticatedUser());

        TextField username = new TextField("Имя пользователя");
        username.setReadOnly(true);

        ComboBox<Coordinate> homeAddress = new ComboBox<>("Домашний адрес");

// Set data items for the ComboBox with a lambda function
        homeAddress.setItems(q -> {
            // Check if the filter is present
            if (q.getFilter().isPresent()) {
                String filter = q.getFilter().get(); // Get the filter text
                return coordinateService.query(filter).stream(); // Get matching coordinates
            } else {
                return Collections.<Coordinate>emptyList().stream(); // Return an empty list
            }
        });

        ComboBox<String> group = new ComboBox<>("Группа");
        group.setItems(groupService.getGroups());


        binder.bind(username, "username");

        binder.bind(homeAddress, "homeAddress");

        binder.withValidator(user -> groupService.groupExists(user.getGroup()), "Такой группы нет!").bind(group, "group");


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

