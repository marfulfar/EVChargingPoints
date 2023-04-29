package com.marful.exampleparsedrive;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.marful.exampleparsedrive.AsyncTasks.MyAsyncTask;
import com.marful.exampleparsedrive.Entities.ChargingPoint;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SplashScreenActivity extends AppCompatActivity {

    List<ChargingPoint> chargingPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        if(!isOnline()) {
            Toast.makeText(SplashScreenActivity.this, "Please Enable GPS and Internet", Toast.LENGTH_LONG).show();
            finishAndRemoveTask();
        }

        Runnable r = () -> {

            try {
                chargingPoints = (List<ChargingPoint>) new MyAsyncTask().execute().get();

            } catch (ExecutionException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {

                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("chargingPoints", (Serializable) chargingPoints);
                startActivity(i);
                // close this activity
                finish();
            }

        };

        Handler h = new Handler();
        h.postDelayed(r,500);



    }//closes main

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
