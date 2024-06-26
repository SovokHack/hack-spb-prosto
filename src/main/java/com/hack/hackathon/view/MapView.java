package com.hack.hackathon.view;


import com.hack.hackathon.dto.RouteDto;
import com.hack.hackathon.entity.Event;
import com.hack.hackathon.enumeration.EventType;
import com.hack.hackathon.layout.MainLayout;
import com.hack.hackathon.security.SecurityService;
import com.hack.hackathon.service.EventExternalService;
import com.hack.hackathon.service.EventService;
import com.hack.hackathon.service.RoutingService;
import com.hack.hackathon.service.WifiExternalService;
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
import com.vaadin.flow.component.map.configuration.style.TextStyle;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.server.StreamResource;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@PageTitle("Map")
@Route(value = "map", layout = MainLayout.class)
public class MapView
        extends HorizontalLayout {
    private final EventExternalService eventExternalService;
    private final SecurityService securityService;
    private final EventService eventService;
    private final WifiExternalService wifiExternalService;
    private final RoutingService routingService;

    Grid<Event> grid = new Grid<>(Event.class, false);
    Map map = new Map();
    Binder<Event> binder = new Binder<>(Event.class);
    Editor<Event> editor = grid.getEditor();
    Event draggedItem;
    MarkerFeature clickedMarker;
    DatePicker datePicker;
    Checkbox wifiSpotsCheckBox = new Checkbox();
    Checkbox externalEventsCheckBox = new Checkbox();
    Button addButton = new Button();

    List<MarkerFeature> routeMarkers = new ArrayList<>();

    public MapView(
            EventExternalService eventExternalService, SecurityService securityService, EventService eventService,
            WifiExternalService wifiExternalService,
            RoutingService routingService) {
        this.eventExternalService = eventExternalService;
        this.eventService = eventService;
        this.wifiExternalService = wifiExternalService;
        this.routingService = routingService;

        TextStyle textStyle = new TextStyle();
        textStyle.setFont("bold 16px sans-serif");
        textStyle.setStroke("#fdf4ff", 3);
        textStyle.setFill("#701a75");
        textStyle.setTextAlign(TextStyle.TextAlign.LEFT);
        textStyle.setOffset(22, - 18);

        clickedMarker = new MarkerFeature();
        clickedMarker.setText("Here");
        clickedMarker.setTextStyle(textStyle);

        List<MarkerFeature> eventMarkers = new ArrayList<>();

        List<MarkerFeature> externalEventsMarkers = new ArrayList<>();

        List<MarkerFeature> scheduleEventMarkers = new ArrayList<>();

        datePicker = new DatePicker(LocalDate.now(), event -> {
            scheduleEventMarkers.forEach(f -> map.getFeatureLayer().removeFeature(f));
            scheduleEventMarkers.clear();

            List<Event> eventList = eventExternalService.retrieveSchedule(
                    securityService.getAuthenticatedUser().getGroup(),
                    LocalDateTime.of(event.getValue(), LocalTime.now()));

            eventList.forEach(
                    eventExternal -> {
                        eventExternal.setType(EventType.EXTERNAL);
                        MarkerFeature markerFeature = new MarkerFeature(
                                eventExternal.getCoordinate() == null ? new Coordinate(30.32F, 59.97F) : new Coordinate(
                                        eventExternal.getCoordinate().getX(), eventExternal.getCoordinate().getY()));
                        markerFeature.setId(eventExternal.getId().toString());
                        scheduleEventMarkers.add(markerFeature);
                    });

            var data = grid.setItems(eventList);
            data.setIdentifierProvider(Event::getId);

            eventExternalService.fetchEvents(0L, 20L, LocalDateTime.of(event.getValue(), LocalTime.now()), null)
                                .forEach(externalEventDto -> {
                                    MarkerFeature markerFeature = new MarkerFeature(
                                            new Coordinate(externalEventDto.getCoordinates().get(1),
                                                           externalEventDto.getCoordinates().get(0)));
                                    markerFeature.setId(externalEventDto.getId().toString());
                                    markerFeature.setText(externalEventDto.getTitle());
                                    externalEventsMarkers.add(markerFeature);
                                });

            scheduleEventMarkers.forEach(f -> {
                f.setDraggable(true);
                map.getFeatureLayer().addFeature(f);
            });
        });
        this.securityService = securityService;

        StreamResource streamResource = new StreamResource("wifi.svg",
                                                           () -> getClass().getResourceAsStream("/images/wifi.svg"));
        Icon.Options usFlagIconOptions = new Icon.Options();
        usFlagIconOptions.setImg(streamResource);

        List<MarkerFeature> wifiSpotsMarkers = wifiExternalService.fetchAll()
                                                                  .map(e -> new MarkerFeature(
                                                                          new Coordinate(e.getCoordinates().get(1),
                                                                                         e.getCoordinates().get(0)),
                                                                          new Icon(usFlagIconOptions)))
                                                                  .toList(); //new ArrayList<>(List.of(new
        // MarkerFeature(new Coordinate(30.31, 59.93), new Icon(usFlagIconOptions))));

        Dialog dialog = new Dialog();

        map.addFeatureClickListener(event -> {
            dialog.removeAll();
            dialog.getFooter().removeAll();
            dialog.add(eventExternalService.getById(Long.valueOf(event.getFeature().getId())).getTitle());
            Button button = new Button("Add", e -> {
                var tmp = eventExternalService.getById(Long.valueOf(event.getFeature().getId()));
                eventService.save(new Event(null, tmp.getTitle(), tmp.getOrganizerAddress(), EventType.OFFLINE,
                                            tmp.getPeriods().get(0).getLower(), tmp.getPeriods().get(0).getUpper(),
                                            new com.hack.hackathon.entity.Coordinate(
                                                    tmp.getCoordinates().get(1).floatValue(),
                                                    tmp.getCoordinates().get(0).floatValue(),
                                                    tmp.getOrganizerAddress()), tmp.getId().toString(), null, null));

                List<Event> eventList = eventExternalService.retrieveSchedule(
                        securityService.getAuthenticatedUser().getGroup(),
                        LocalDateTime.of(datePicker.getValue(), LocalTime.now()));

                eventList.forEach(
                        eventExternal -> {
                            eventExternal.setType(EventType.EXTERNAL);
                            MarkerFeature markerFeature = new MarkerFeature(
                                    eventExternal.getCoordinate() == null ? new Coordinate(30.32F,
                                                                                           59.97F) : new Coordinate(
                                            eventExternal.getCoordinate().getX(),
                                            eventExternal.getCoordinate().getY()));
                            markerFeature.setId(eventExternal.getId().toString());
                            scheduleEventMarkers.add(markerFeature);
                        });

                grid.setItems(eventList);
                dialog.close();
            });
            dialog.getFooter().add(button);
            dialog.open();
        });

        map.setCenter(new Coordinate(30.31, 59.93));
        map.setZoom(10);

        grid.addItemClickListener(e -> {
            Event event = e.getItem();
            if(event != null && event.getCoordinate() != null) {
                map.setCenter(new Coordinate(event.getCoordinate().getX(), event.getCoordinate().getY()));
                map.setZoom(14);

                clickedMarker.setCoordinates(
                        new Coordinate(event.getCoordinate().getX(), event.getCoordinate().getY()));
                map.getFeatureLayer().addFeature(clickedMarker);
            }
        });

        map.addViewMoveEndEventListener(event -> {
            clickedMarker.setCoordinates(new Coordinate(0, 0));
            map.getFeatureLayer().removeFeature(clickedMarker);
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
        wifiSpotsCheckBox.addValueChangeListener(event -> {
            if(event.getValue()) {
                wifiSpotsMarkers.forEach(markerFeature -> map.getFeatureLayer().addFeature(markerFeature));
            }
            else {
                wifiSpotsMarkers.forEach(markerFeature -> map.getFeatureLayer().removeFeature(markerFeature));
            }
        });

        addButton.setText("New");
        addButton.addClickListener(event -> {
            List<Event> eventList = eventExternalService.retrieveSchedule(
                    securityService.getAuthenticatedUser().getGroup(),
                    LocalDateTime.of(datePicker.getValue(), LocalTime.now()));

            eventList.forEach(
                    eventExternal -> {
                        eventExternal.setType(EventType.EXTERNAL);
                        MarkerFeature markerFeature = new MarkerFeature(new Coordinate(30.31, 59.93));
                        markerFeature.setId(eventExternal.getId().toString());
                        scheduleEventMarkers.add(markerFeature);
                    });

            Event event1 = new Event(null, "Event", "Description", EventType.OFFLINE,
                                     LocalDateTime.of(datePicker.getValue(), LocalTime.now()),
                                     LocalDateTime.of(datePicker.getValue(), LocalTime.now().plusHours(1)),
                                     new com.hack.hackathon.entity.Coordinate(30.31F, 59.93F, "adr"), null, null, null);

            eventList.add(eventService.create(event1));

            grid.setItems(eventList).setIdentifierProvider(Event::getId);

            MarkerFeature markerFeature = new MarkerFeature(
                    new Coordinate(event1.getCoordinate().getX(), event1.getCoordinate().getY()));
            markerFeature.setId(event1.getId().toString());
            markerFeature.setDraggable(true);

            eventMarkers.add(markerFeature);
            map.getFeatureLayer().addFeature(markerFeature);
        });

        map.addFeatureDropListener(event -> {
            MarkerFeature droppedMarker = (MarkerFeature) event.getFeature();
            Coordinate endCoordinates = event.getCoordinate();

            Event event1 = eventService.getById(Long.valueOf(droppedMarker.getId()));
            event1.setCoordinate(new com.hack.hackathon.entity.Coordinate(
                    (float) endCoordinates.getX(), (float) endCoordinates.getY(), ""));
            eventService.save(event1);

            clickedMarker.setCoordinates(new Coordinate(0, 0));
            map.getFeatureLayer().removeFeature(clickedMarker);
        });

        setupEventData();
        setupLayout();
        setupGrid();
        setupDragAndDrop();
    }

    private void setupEventData() {
    }

    private void drawRoute(Double startX, Double startY, Double endX, Double endY) {
        List<RouteDto> list = routingService.route(startX, startY, endX, endY);

        routeMarkers = generateMarkersWithInterpolation(list);

        routeMarkers.forEach(routeMarker -> map.getFeatureLayer().addFeature(routeMarker));
    }

    public List<MarkerFeature> generateMarkersWithInterpolation(List<RouteDto> list) {
        List<MarkerFeature> routeMarkers = new ArrayList<>();

        StreamResource streamResource = new StreamResource("route.svg",
                                                           () -> getClass().getResourceAsStream("/images/route.svg"));
        Icon.Options usFlagIconOptions = new Icon.Options();
        usFlagIconOptions.setImg(streamResource);

        for(int i = 0; i < list.size() - 1; i++) {
            RouteDto startPoint = list.get(i);
            RouteDto endPoint = list.get(i + 1);

            // Добавляем начальную точку
            routeMarkers.add(new MarkerFeature(new Coordinate(startPoint.getX(), startPoint.getY()),
                                               new Icon(usFlagIconOptions)));

            // Проверяем расстояние между точками
            double distanceX = endPoint.getX() - startPoint.getX();
            double distanceY = endPoint.getY() - startPoint.getY();
            double totalDistance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);

            // Добавляем промежуточные точки
            if(totalDistance > 0) {
                int numInterpolatedPoints = 5; // Желаемое количество промежуточных точек
                for(int j = 1; j <= numInterpolatedPoints; j++) {
                    double interpolatedX = startPoint.getX() + j * distanceX / (numInterpolatedPoints + 1);
                    double interpolatedY = startPoint.getY() + j * distanceY / (numInterpolatedPoints + 1);
                    routeMarkers.add(new MarkerFeature(new Coordinate(interpolatedX, interpolatedY),
                                                       new Icon(usFlagIconOptions)));
                }
            }
        }

        // Добавляем последнюю точку
        RouteDto lastPoint = list.get(list.size() - 1);
        routeMarkers.add(
                new MarkerFeature(new Coordinate(lastPoint.getX(), lastPoint.getY()), new Icon(usFlagIconOptions)));

        return routeMarkers;
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
        rightView.setWidth("60%");
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
        horizontalLayout.add(addButton);

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
        grid.addComponentColumn(this::createRouteButton).setWidth("150px").setFlexGrow(0);

        binder.forField(nameField).bind(Event::getName, Event::setName);
        binder.forField(startTime).bind(e -> e.getStartTime().toLocalTime(), (e, localTime) -> e.setStartTime(
                LocalDateTime.of(datePicker.getValue(), localTime)));
        binder.forField(endTime).bind(e -> e.getEndTime().toLocalTime(), (e, localTime) -> e.setEndTime(
                LocalDateTime.of(datePicker.getValue(), localTime)));

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

    private Button createRouteButton(Event event) {
        Button editButton = new Button("Route");
        editButton.addClickListener(e -> {
            routeMarkers.forEach(route -> map.getFeatureLayer().removeFeature(route));
            routeMarkers.clear();

            drawRoute(securityService.getAuthenticatedUser().getHomeAddress().getX().doubleValue(), securityService.getAuthenticatedUser().getHomeAddress().getY().doubleValue(), event.getCoordinate() == null ? 30.32F : event.getCoordinate().getX().doubleValue(), event.getCoordinate() == null ? 59.97F : event.getCoordinate().getY().doubleValue());
        });
        return editButton;
    }

    private VerticalLayout createActionsLayout() {
        Button saveButton = new Button("Save", e -> {
            eventService.save(editor.getItem());
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

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Filter {
        private LocalDate time;
    }
}
