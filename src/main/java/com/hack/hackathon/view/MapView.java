package com.hack.hackathon.view;


import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import com.hack.hackathon.layout.MainLayout;
import com.hack.hackathon.service.EventService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Pageable;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AnonymousAllowed
@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
@RequiredArgsConstructor
public class MapView
        extends HorizontalLayout {
    List<Event> eventList = new ArrayList<>();
    Grid<Event> grid = new Grid<>(Event.class, false);
    MapLibre map = Components.getMap();
    Binder<Event> binder = new Binder<>(Event.class);
    Editor<Event> editor = grid.getEditor();
    Event draggedItem;

    {
        setupEventData();
        setupLayout();
        setupGrid();
        setupDragAndDrop();

        eventList.forEach(event -> map.addMarker(event.getCoordinate().x, event.getCoordinate().y).withPopup(
                "<h3>" + event.getName()  + "</h3>" ));

    }

    private void setupEventData() {

    }

    private void setupLayout() {
        setWidth("100%");
        setPadding(false);
        setSpacing(false);

        VerticalLayout leftView = new VerticalLayout();
        leftView.setWidth("100%");
        leftView.setMinHeight("90vh");
        leftView.setPadding(false);
        leftView.setSpacing(false);

        VerticalLayout rightView = new VerticalLayout();
        rightView.setWidth("50%");
        rightView.setMinHeight("90vh");
        rightView.setPadding(false);
        rightView.setSpacing(false);

        add(leftView, rightView);

        leftView.add(map);
        rightView.add(grid);
    }

    private void setupGrid() {
        grid.setItems(eventList);
        grid.setWidthFull();
        grid.setDropMode(GridDropMode.BETWEEN);
        grid.setRowsDraggable(true);
        grid.addDragStartListener(e -> draggedItem = e.getDraggedItems().get(0));
        grid.addDragEndListener(e -> draggedItem = null);

        setupEditorComponentsAndGridColumns();
    }

    private void setupEditorComponentsAndGridColumns() {
        TextField nameField = new TextField();
        TimePicker startTime = new TimePicker();
        TimePicker endTime = new TimePicker();
        TextField descriptionField = new TextField();

        grid.addComponentColumn(EventView::new).setEditorComponent(new EventEditView(nameField, startTime, endTime, descriptionField));
        grid.addComponentColumn(this::createEditButton).setWidth("150px").setFlexGrow(0).setEditorComponent(createActionsLayout());

        binder.forField(nameField).bind(Event::getName, Event::setName);
        binder.forField(startTime).bind(Event::getStartTime, Event::setStartTime);
        binder.forField(endTime).bind(Event::getEndTime, Event::setEndTime);
        binder.forField(descriptionField).bind(Event::getDescription, Event::setDescription);
        editor.setBinder(binder);
        editor.setBuffered(true);
    }

    private Button createEditButton(Event event) {
        Button editButton = new Button("Edit");
        editButton.addClickListener(e -> {
            if(editor.isOpen()) {
                editor.cancel();
            }
            binder.setBean(event);
            editor.editItem(event);
        });
        if(event.getType().equals(EventType.EXTERNAL)) {
            return null;

        }
        return editButton;
    }

    private VerticalLayout createActionsLayout(){
        Button saveButton = new Button("Save", e -> {
            editor.save();
            editor.cancel();
        });
        Button cancelButton = new Button("Cancel", e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return new VerticalLayout(saveButton, cancelButton);
    }

    private void setupDragAndDrop() {
        GridListDataView<Event> dataView = grid.setItems(eventList);
        dataView.setIdentifierProvider(Event::getId);

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
    }
}
