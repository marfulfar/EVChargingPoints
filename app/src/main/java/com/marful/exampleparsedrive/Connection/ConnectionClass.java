package com.marful.exampleparsedrive.Connection;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ConnectionClass {

    final static String CHARGING_STATIONS = "https://analisi.transparenciacatalunya.cat/resource/tb2m-m33b.json";
    static String jsonString;
    static HttpURLConnection connection = null;
    static BufferedReader reader = null;
    static URL url;
    static StringBuffer buffer;
    JSONArray jsonArray;


    public ConnectionClass() {

    }

    public JSONArray getJsonArray() throws MalformedURLException, JSONException {

        try {
            url = new URL(CHARGING_STATIONS);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));

            String line = "";
            buffer = new StringBuffer();

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            jsonString = buffer.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    return jsonArray = new JSONArray(jsonString);

    }

    }//closes class

