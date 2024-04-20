package com.hack.hackathon.view;


import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import com.hack.hackathon.layout.MainLayout;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.vaadin.addons.maplibre.MapLibre;

import java.sql.Array;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AnonymousAllowed
@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
@RequiredArgsConstructor
public class MapView
        extends HorizontalLayout {
    private Event draggedItem;
    {
        List<Event> eventList = new ArrayList<>(
                List.of(new Event(1L, "Event 1", "Description for Event 1", EventType.REMOTE, LocalTime.now(),
                                  LocalTime.now().plusHours(2), "Location for Event 1"),
                        new Event(2L, "Event 2", "Description for Event 2", EventType.REMOTE, LocalTime.now(),
                                  LocalTime.now().plusHours(2), "Location for Event 2"),
                        new Event(3L, "Event 3", "Description for Event 3", EventType.REMOTE, LocalTime.now(),
                                  LocalTime.now().plusHours(2), "Location for Event 3")));

        setWidth("100%");
        setPadding(false);
        setSpacing(false);

        VerticalLayout leftView = new VerticalLayout();
        leftView.setWidth("100%");
        leftView.setPadding(false);
        leftView.setSpacing(false);

        VerticalLayout rightView = new VerticalLayout();
        rightView.setWidth("50%");
        rightView.setPadding(false);
        rightView.setSpacing(false);

        add(leftView, rightView);

        MapLibre map = Components.getMap();
        map.setHeight("90vh");

        Grid<Event> grid = new Grid<>(Event.class, false);

        Editor<Event> editor = grid.getEditor();
        Binder<Event> binder = new Binder<>(Event.class);
        Grid.Column<Event> eventViewColumn = grid.addColumn(new ComponentRenderer<>(EventView::new));
        Grid.Column<Event> editColumn = grid.addComponentColumn(event -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> {
                if (editor.isOpen())
                    editor.cancel();
                binder.setBean(event);
                editor.editItem(event);
            });

            return editButton;
        }).setWidth("150px").setFlexGrow(0);

        TextField nameField = new TextField();
        TimePicker startTime = new TimePicker();
        TimePicker endTime = new TimePicker();
        TextField descriptionField = new TextField();

        binder.forField(nameField).bind(Event::getName, Event::setName);

        binder.forField(startTime).bind(Event::getStartTime, Event::setStartTime);

        binder.forField(endTime).bind(Event::getEndTime, Event::setEndTime);

        binder.forField(descriptionField).bind(Event::getDescription, Event::setDescription);

        eventViewColumn.setEditorComponent(new EventEditView(nameField, startTime, endTime, descriptionField));

        editor.setBinder(binder);
        editor.setBuffered(true);

        Button saveButton = new Button("Save", e -> {editor.save(); editor.cancel();});
        Button cancelButton = new Button("Cancel", e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        VerticalLayout actions = new VerticalLayout(saveButton, cancelButton);
        actions.setPadding(false);
        editColumn.setEditorComponent(actions);



        grid.setDropMode(GridDropMode.BETWEEN);
        grid.setRowsDraggable(true);

        GridListDataView<Event> dataView = grid.setItems(eventList);
        dataView.setIdentifierProvider(Event::getId);
        grid.addDragStartListener(e -> draggedItem = e.getDraggedItems().get(0));

        grid.addDropListener(e -> {
            Event targetEvent = e.getDropTargetItem().orElse(null);
            GridDropLocation dropLocation = e.getDropLocation();

            if(targetEvent == null || draggedItem.equals(targetEvent)) {
                return;
            }

            dataView.removeItem(draggedItem);

            if(dropLocation == GridDropLocation.BELOW) {
                dataView.addItemAfter(draggedItem, targetEvent);
            }
            else {
                dataView.addItemBefore(draggedItem, targetEvent);
            }
        });

        grid.addDragEndListener(e -> draggedItem = null);

        leftView.add(map);
        rightView.add(grid);
    }
}
