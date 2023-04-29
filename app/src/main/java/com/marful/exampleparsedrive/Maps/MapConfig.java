package com.marful.exampleparsedrive.Maps;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.preference.PreferenceManager;

import androidx.annotation.RequiresApi;

import com.marful.exampleparsedrive.BuildConfig;
import com.marful.exampleparsedrive.Entities.PuntCarrega;
import com.marful.exampleparsedrive.R;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MapConfig {

    Marker destinyMarker,startMarker;
    Context context;
    MapView map;

    public MapConfig(Context currentContext, MapView currentMap) {
        this.context = currentContext;
        this.map = currentMap;
    }

    public void loadMaps(){
        Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context));
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
    }

    public void centeringMap(double zoom, Location myLocation){
        IMapController mapController = map.getController();
        mapController.setZoom(zoom);
        GeoPoint startPoint = new GeoPoint(myLocation.getLatitude(),myLocation.getLongitude());
        mapController.setCenter(startPoint);

    }

    public Marker addingStartMarker(Location myLocation){
        startMarker = new Marker(map);
        startMarker.setPosition(new GeoPoint(myLocation.getLatitude(),myLocation.getLongitude()));
        startMarker.setIcon(context.getDrawable(R.drawable.baseline_circle_24));
        startMarker.setTitle("Start point");
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        map.getOverlays().add(startMarker);

        return startMarker;
    }

    public Marker addingDestinyMarker(Location myDestiny){
        destinyMarker = new Marker(map);
        destinyMarker.setPosition(new GeoPoint(myDestiny.getLatitude(),myDestiny.getLongitude()));
        destinyMarker.setIcon(context.getDrawable(R.drawable.baseline_location_on_48));
        destinyMarker.setTitle("Destiny point");
        destinyMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        map.getOverlays().add(destinyMarker);

        return destinyMarker;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void addingOverlay(List<PuntCarrega> puntsCarregaSorted){
        //TODO option to filter markers by "municipi", adding filter to the lambda
        /*
        List<PuntCarrega> filteredMunicipi = puntsCarregaSorted.stream()
                .filter(m -> m.getMunicipi().equalsIgnoreCase("barcelona"))
                .collect(Collectors.toList());
        */

        puntsCarregaSorted.stream().forEach(p->{
            Marker mark = new Marker(map);
            mark.setIcon(context.getDrawable(R.drawable.location_green_48));
            mark.setTitle("EV charging point");
            mark.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            mark.setPosition(new GeoPoint(p.getLatitude(), p.getLongitude()));
            map.getOverlays().add(mark);
        });


    }


    public void addingRouteLine(Location myLocation, Location myDestiny) {
        ArrayList<GeoPoint> wayPoints = new ArrayList<>();
        RoadManager roadManager = new OSRMRoadManager(context,"MyUserAgent");

        wayPoints.add(new GeoPoint(myLocation));
        wayPoints.add(new GeoPoint(myDestiny));
        Road road = roadManager.getRoad(wayPoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);
        map.getOverlays().add(1,roadOverlay);

    }


}//closes class
