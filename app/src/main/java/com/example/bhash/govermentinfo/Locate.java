package com.example.bhash.govermentinfo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by bhash on 10-08-2017.
 */

public class Locate {
    private HomeLocation access;
    private LocationManager manager;
    private LocationListener listener;

    public Locate(HomeLocation h) {
        access = h;
        if (checkPermission()) {
            setuploactionmanager();
            findlocation();

        }
    }

    public void setuploactionmanager() {
        if (manager != null) {
            return;
        }
        if (!checkPermission()) {
            return;
        }

        manager = (LocationManager) access.getSystemService(Context.LOCATION_SERVICE);
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                access.setit(location.getLatitude(), location.getLongitude());

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
        };
       // manager.requestLocationUpdates(manager.GPS_PROVIDER,1000,0,listener);
    }

    public void findlocation() {
        if (!checkPermission())
            return;

        if (manager == null)
            setuploactionmanager();

        if (manager != null) {
            Location loc = manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (loc != null) {
                access.setit(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(access, "Using " + LocationManager.NETWORK_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (manager != null) {
            Location loc = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (loc != null) {
                access.setit(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(access, "Using " + LocationManager.PASSIVE_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (manager != null) {
            Location loc = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (loc != null) {
                access.setit(loc.getLatitude(), loc.getLongitude());
                Toast.makeText(access, "Using " + LocationManager.GPS_PROVIDER + " Location provider", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        // If you get here, you got no location at all
        access.noLocationAvailable();
        return;
    }

    public void shutdown() {
        if (ActivityCompat.checkSelfPermission(access, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.removeUpdates(listener);
        manager = null;
    }

    public boolean checkPermission(){
        if (ContextCompat.checkSelfPermission(access, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(access,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 5);
            return false;
        }
        return true;

    }
}
