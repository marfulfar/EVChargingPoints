package com.marful.exampleparsedrive.AsyncTasks;

import android.os.AsyncTask;

import com.marful.exampleparsedrive.Connection.ConnectionClass;
import com.marful.exampleparsedrive.Entities.PuntCarrega;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class MyAsyncTask extends AsyncTask {
    @Override
    protected List<PuntCarrega>  doInBackground(Object[] objects) {
        ConnectionClass myConnection = new ConnectionClass();
        List<PuntCarrega> puntsCarrega;
        JSONArray jsonArray;

        try {
           jsonArray = myConnection.getJsonObject(myConnection.getJsonString());

           puntsCarrega = parsePuntsCarrega(jsonArray);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }



        return puntsCarrega;
    }


    private static List<PuntCarrega> parsePuntsCarrega(JSONArray jsonArray) throws JSONException {
        List<PuntCarrega> puntsCarrega = new ArrayList<>();

        for (int i =0; i < jsonArray.length(); i++){

            String id = jsonArray.getJSONObject(i).getString("id");
            String municipi = jsonArray.getJSONObject(i).getString("municipi");
            String provincia = jsonArray.getJSONObject(i).getString("provincia");
            String latitud = jsonArray.getJSONObject(i).getString("latitud");
            String longitud = jsonArray.getJSONObject(i).getString("longitud");

            PuntCarrega pc = new PuntCarrega(Double.parseDouble(id),municipi,provincia,Double.parseDouble(latitud),Double.parseDouble(longitud),0f);
            puntsCarrega.add(pc);
        }

        return puntsCarrega;
    }


}
