package com.marful.exampleparsedrive.AsyncTasks;

import android.os.AsyncTask;

import com.marful.exampleparsedrive.Connection.ConnectionClass;
import com.marful.exampleparsedrive.Entities.ChargingPoint;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class MyAsyncTask extends AsyncTask {
    @Override
    protected List<ChargingPoint>  doInBackground(Object[] objects) {
        ConnectionClass myConnection = new ConnectionClass();
        List<ChargingPoint> chargingPoints;

        try {
           chargingPoints = parseChargingPointsArray(myConnection.getJsonArray());

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        return chargingPoints;
    }


    private static List<ChargingPoint> parseChargingPointsArray(JSONArray jsonArray) throws JSONException {
        List<ChargingPoint> chargingPoints = new ArrayList<>();

        for (int i =0; i < jsonArray.length(); i++){

            //String id = jsonArray.getJSONObject(i).getString("id");
            String municipi = jsonArray.getJSONObject(i).getString("municipi");
            String provincia = jsonArray.getJSONObject(i).getString("provincia");
            String latitude = jsonArray.getJSONObject(i).getString("latitud");
            String longitude = jsonArray.getJSONObject(i).getString("longitud");

            ChargingPoint cp = new ChargingPoint(municipi,provincia,Double.parseDouble(latitude),Double.parseDouble(longitude),0f);
            chargingPoints.add(cp);
        }

        return chargingPoints;
    }


}
