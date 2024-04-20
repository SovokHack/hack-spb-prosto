package com.hack.hackathon;

import com.hack.hackathon.layout.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;
import org.vaadin.addons.maplibre.MapLibre;
import org.vaadin.addons.maplibre.Marker;
import java.net.URI;
import java.net.URISyntaxException;


@Route(value = "java", layout = MainLayout.class)
public class JavaView extends Div {
    private Marker yourPosition;
    {
        try {
            MapLibre map = new MapLibre(new URI("https://api.maptiler.com/maps/streets/style.json?key=VR1fHJnaqxihDwaoqdjp"));
            map.setHeight("400px");
            map.setWidth("100%");
            map.addMarker(22.300, 60.452).withPopup("Hello from Vaadin!");

            Polygon polygon = (Polygon) new WKTReader().read("POLYGON((22.290 60.428, 22.310 60.429, 22.31 60.47, 22.28 60.47, 22.290 60.428))");

            //            map.addFillLayer(polygon, "{'fill-color': 'red', 'fill-opacity': 0.2}");

            map.setCenter(22.300, 60.452);
            map.setZoomLevel(13);
            add(map);

            Button b = new Button("Zoom to content");
            b.addClickListener(e -> {
                Geometry g = polygon;
                if(yourPosition != null) {
                    g = g.union(yourPosition.getGeometry());
                }
                map.fitTo(g, 0.01);
            });
            Button seeWorld = new Button("See the world (flyTo(0,0,0)");
            seeWorld.addClickListener(e -> {
                map.flyTo(0,0,0.0);
            });
            Button plotYourself = new Button("Plot yourself");
            plotYourself.addClickListener(e -> {
            });
            add(new HorizontalLayout(b, seeWorld, plotYourself));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (org.locationtech.jts.io.ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
