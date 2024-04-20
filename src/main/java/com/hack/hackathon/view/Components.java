package com.hack.hackathon.view;

import org.vaadin.addons.maplibre.MapLibre;

import java.net.URI;
import java.net.URISyntaxException;

public class Components {
    public static MapLibre getMap() {
        MapLibre map;

        try {
            map = new MapLibre(new URI("https://api.maptiler.com/maps/streets/style.json?key=VR1fHJnaqxihDwaoqdjp"));
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return map;
    }
}
