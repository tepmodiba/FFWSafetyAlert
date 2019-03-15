package com.dynamicminds.ffwsafetyalert;


import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class LocationMonitor implements LocationListener {
    Context context;


    LocationMonitor(Context context) {
        this.context = context;
        //Toast.makeText(context, "Toast!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        MainActivity.LATITUDE = location.getLatitude();
        MainActivity.LONGITUDE = location.getLongitude();

        Log.d("Location: ", location.getLatitude() + ", " + location.getLongitude());

        //MainActivity.mTextMessage.setText("Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
        Toast.makeText(context, location.getLatitude() + " | - | " + location.getLongitude(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
