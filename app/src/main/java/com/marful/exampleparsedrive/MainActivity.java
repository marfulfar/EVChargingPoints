package com.marful.exampleparsedrive;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.marful.exampleparsedrive.AsyncTasks.MyAsyncTask;
import com.marful.exampleparsedrive.Connection.ConnectionClass;
import com.marful.exampleparsedrive.Entities.ChargingPoint;
import com.marful.exampleparsedrive.Maps.MapConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements LocationListener {

    MapView map;
    static List<ChargingPoint> chargingPoints;
    static LocationManager locationManager;
    static Location myLocation, myDestiny;
    static List<ChargingPoint> puntsCarregaSorted;
    Marker destinyMarker,startMarker;
    private MapConfig myMapConfig;


    //ANDROID 7
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //to overrride the internet on main thread
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        map = (MapView) findViewById(R.id.map);

        Intent i = getIntent();
        chargingPoints = (List<ChargingPoint>) i.getSerializableExtra("chargingPoints");

        myMapConfig = new MapConfig(getApplicationContext(), map);


        if(isOnline()) {
            getLocation();
        }else{
            Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_LONG).show();
            finishAndRemoveTask();
        }


        myMapConfig.loadMaps();

        myMapConfig.centeringMap(15,myLocation);

        puntsCarregaSorted = calculatingDistances(chargingPoints);

        myDestiny = gettingDestiny(puntsCarregaSorted);

        myMapConfig.addingOverlay(puntsCarregaSorted);

        Log.i("myLocation",""+myLocation); //debug purposes
        startMarker = myMapConfig.addingStartMarker(myLocation);

        Log.i("myDestiny",""+myDestiny); //debug purposes
        destinyMarker = myMapConfig.addingDestinyMarker(myDestiny);

        myMapConfig.addingRouteLine(myLocation,myDestiny);



    }//closes main






    public void getLocation() {

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {
            // Start listening for location updates
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        }
        myLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(Location location) {
        myLocation = location;

        puntsCarregaSorted = calculatingDistances(chargingPoints);

        double zoom = map.getZoomLevelDouble();
        myMapConfig.centeringMap(zoom, myLocation);

        myDestiny = gettingDestiny(puntsCarregaSorted);

        map.getOverlays().remove(destinyMarker);
        map.getOverlays().remove(startMarker);

        startMarker = myMapConfig.addingStartMarker(myLocation);
        destinyMarker = myMapConfig.addingDestinyMarker(myDestiny);

        map.getOverlays().remove(1);
        myMapConfig.addingRouteLine(myLocation, myDestiny);


    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(MainActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }



    private static List<ChargingPoint> parsePuntsCarrega(JSONArray jsonArray) throws JSONException {
        chargingPoints = new ArrayList<>();

        for (int i =0; i < jsonArray.length(); i++){

            String id = jsonArray.getJSONObject(i).getString("id");
            String municipi = jsonArray.getJSONObject(i).getString("municipi");
            String provincia = jsonArray.getJSONObject(i).getString("provincia");
            String latitud = jsonArray.getJSONObject(i).getString("latitud");
            String longitud = jsonArray.getJSONObject(i).getString("longitud");

            ChargingPoint pc = new ChargingPoint(Double.parseDouble(id),municipi,provincia,Double.parseDouble(latitud),Double.parseDouble(longitud),0f);
            chargingPoints.add(pc);
        }

        return chargingPoints;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private List<ChargingPoint> calculatingDistances(List<ChargingPoint> chargingPoints){
        //TODO calculate by road distance not by straight distance

        Location l = new Location("dummy");

        chargingPoints.stream().forEach(p->{
            l.setLatitude(p.getLatitude());
            l.setLongitude(p.getLongitude());
            Float distanceToUser = myLocation.distanceTo(l)/1000;
            p.setDistance(distanceToUser);
        });

        puntsCarregaSorted = chargingPoints.stream()
                .sorted((o1, o2) -> Float.compare (o1.getDistance(),o2.getDistance()))
                .collect(Collectors.toList());


        return puntsCarregaSorted;
    }

    private Location gettingDestiny (List<ChargingPoint> puntsCarregaSorted){
        myDestiny = new Location("myDestiny");
        myDestiny.setLatitude(puntsCarregaSorted.get(0).getLatitude());
        myDestiny.setLongitude(puntsCarregaSorted.get(0).getLongitude());
        return myDestiny;
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }




}//closes class