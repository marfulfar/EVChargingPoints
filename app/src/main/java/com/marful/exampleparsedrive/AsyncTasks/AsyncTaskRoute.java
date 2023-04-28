package com.marful.exampleparsedrive.AsyncTasks;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.os.AsyncTask;

import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;

public class AsyncTaskRoute extends AsyncTask {



    @Override
    protected Polyline doInBackground(Object[] objects) {

        RoadManager roadManager = new OSRMRoadManager((Context)objects[2],"MyUserAgent");
        ArrayList<GeoPoint> wayPoints = new ArrayList<>();
        wayPoints.add(new GeoPoint((Location) objects[0]));
        wayPoints.add(new GeoPoint((Location) objects[1]));
        Road road = roadManager.getRoad(wayPoints);
        Polyline roadOverlay = RoadManager.buildRoadOverlay(road);

        return roadOverlay;
    }
}
