package com.hack.hackathon.view;


import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import com.hack.hackathon.layout.MainLayout;
import com.hack.hackathon.service.EventExternalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.grid.dnd.GridDropLocation;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.map.Map;
import com.vaadin.flow.component.map.configuration.Coordinate;
import com.vaadin.flow.component.map.configuration.feature.MarkerFeature;
import com.vaadin.flow.component.map.configuration.style.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AnonymousAllowed
@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
public class MapView
        extends HorizontalLayout {
    private final EventExternalService eventExternalService;
    Grid<Event> grid = new Grid<>(Event.class, false);
    Map map = new Map();
    Binder<Event> binder = new Binder<>(Event.class);
    Editor<Event> editor = grid.getEditor();
    Event draggedItem;
    Event clickedItem;
    DatePicker datePicker;
    Checkbox wifiSpotsCheckBox = new Checkbox();
    Checkbox externalEventsCheckBox = new Checkbox();
    ;

    public MapView(EventExternalService eventExternalService) {
        this.eventExternalService = eventExternalService;

        datePicker = new DatePicker(event -> {
            grid.setItems(eventExternalService.fetchEvents(1L, 10L, event.getValue().atStartOfDay(),
                                                           event.getValue().atStartOfDay().plusDays(1L))
                                              .stream()
                                              .map(o -> Event.builder()
                                                             .externalId(o.getId().toString())
                                                             .name(o.getTitle())
                                                             .type(EventType.OFFLINE)
                                                             .startTime(LocalDateTime.now().toLocalTime())
                                                             .endTime(LocalDateTime.now().toLocalTime())
                                                             .coordinate(new com.hack.hackathon.entity.Coordinate(
                                                                     o.getCoordinates().get(0).floatValue(),
                                                                     o.getCoordinates().get(1).floatValue(),
                                                                     o.getOrganizerAddress()))
                                                             .build())
                                              .toList());
        });

        List<Event> eventList = new ArrayList<>();

        List<Event> externalEventList = new ArrayList<>();

        externalEventList.add(new Event(1L, "Event 1", "Description 1", EventType.OFFLINE,
                                        LocalTime.of(10, 0),
                                        LocalTime.of(12, 0),
                                        new com.hack.hackathon.entity.Coordinate(1F, 1F, "adr1"), "id1", "link1",
                                        null));

        externalEventList.add(new Event(2L, "Event 2", "Description 2", EventType.ONLINE,
                                        LocalTime.of(11, 0),
                                        LocalTime.of(13, 0),
                                        new com.hack.hackathon.entity.Coordinate(2F, 2F, "adr2"), "id2", "link2",
                                        null));

        externalEventList.add(new Event(3L, "Event 3", "Description 3", EventType.OFFLINE,
                                        LocalTime.of(12, 0),
                                        LocalTime.of(14, 0),
                                        new com.hack.hackathon.entity.Coordinate(3F, 3F, "adr3"), "id3", "link3",
                                        null));

        externalEventList.add(new Event(4L, "Event 4", "Description 4", EventType.ONLINE,
                                        LocalTime.of(13, 0),
                                        LocalTime.of(15, 0),
                                        new com.hack.hackathon.entity.Coordinate(4F, 4F, "adr4"), "id4", "link4",
                                        null));

        externalEventList.add(new Event(5L, "Event 5", "Description 5", EventType.OFFLINE,
                                        LocalTime.of(14, 0),
                                        LocalTime.of(16, 0),
                                        new com.hack.hackathon.entity.Coordinate(5F, 5F, "adr5"), "id5", "link5",
                                        null));

        List<MarkerFeature> wifiSpotsMarkers = new ArrayList<>();

        List<MarkerFeature> externalEventsMarkers = new ArrayList<>();

        Dialog dialog = new Dialog();

        map.addFeatureClickListener(event -> {
            clickedItem = externalEventList.get(0);
            dialog.open();
        });//eventExternalService.getById(Long.valueOf(event.getFeature().getId()));


        externalEventList.forEach(event -> {
            MarkerFeature markerFeature = new MarkerFeature(
                    new Coordinate(event.getCoordinate().getX(), event.getCoordinate().getY()));
            markerFeature.setId(event.getExternalId());
            markerFeature.setText(event.getName());
            externalEventsMarkers.add(markerFeature);
        });

        externalEventsCheckBox.setLabel("Show External Events");
        externalEventsCheckBox.addValueChangeListener(event -> {
            if(event.getValue()) {
                externalEventsMarkers.forEach(markerFeature -> map.getFeatureLayer().addFeature(markerFeature));
            }
            else {
                externalEventsMarkers.forEach(markerFeature -> map.getFeatureLayer().removeFeature(markerFeature));
            }
        });

        wifiSpotsCheckBox.setLabel("Show WiFi Spots");
        wifiSpotsCheckBox.addValueChangeListener(event -> {});

        setupEventData();
        setupLayout();
        setupGrid();
        setupDragAndDrop();

        Coordinate germanOfficeCoordinates = new Coordinate(13.45489, 52.51390);

        StreamResource streamResource = new StreamResource("wifi.svg",
                                                           () -> getClass().getResourceAsStream("/images/wifi.svg"));
        Icon.Options usFlagIconOptions = new Icon.Options();
        usFlagIconOptions.setImg(streamResource);
        Icon usFlagIcon = new Icon(usFlagIconOptions);
        MarkerFeature usOffice = new MarkerFeature(germanOfficeCoordinates, usFlagIcon);

        map.getFeatureLayer().addFeature(usOffice);
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

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setPadding(true);
        horizontalLayout.setMargin(false);
        horizontalLayout.setAlignItems(Alignment.CENTER);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.START);
        horizontalLayout.add(datePicker);
        horizontalLayout.add(wifiSpotsCheckBox);
        horizontalLayout.add(externalEventsCheckBox);

        add(leftView, rightView);

        leftView.add(map);
        rightView.add(horizontalLayout, grid);
    }

    private void setupGrid() {
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

        grid.addComponentColumn(EventView::new).setEditorComponent(
                new EventEditView(nameField, startTime, endTime, descriptionField));
        grid.addComponentColumn(this::createEditButton).setWidth("150px").setFlexGrow(0).setEditorComponent(
                createActionsLayout());

        binder.forField(nameField).bind(Event::getName, Event::setName);
        //binder.forField(startTime).bind(Event::getStartTime, Event::setStartTime);
        //binder.forField(endTime).bind(Event::getEndTime, Event::setEndTime);
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

    private VerticalLayout createActionsLayout() {
        Button saveButton = new Button("Save", e -> {
            editor.save();
            editor.cancel();
        });
        Button cancelButton = new Button("Cancel", e -> editor.cancel());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        return new VerticalLayout(saveButton, cancelButton);
    }

    private void setupDragAndDrop() {
        GridListDataView<Event> dataView = grid.getListDataView();
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
