package com.example.rharper.foundsound;

import android.app.Application;
import android.location.Location;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationService {

    private Application app;
    private FusedLocationProviderClient mFusedLocationProvider;
    private Location mLastKnownLocation;
    private LocationResponseCallback locationResponse;

    public LocationService(Application application, LocationResponseCallback locationResponseCallback) {
        app = application;
        mFusedLocationProvider = LocationServices.getFusedLocationProviderClient(app);
        locationResponse = locationResponseCallback;
    }

    public void getDeviceLocation() {
        try {
                Task<Location> locationResult = mFusedLocationProvider.getLastLocation();
                locationResult.addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if(task.isSuccessful()){
                            mLastKnownLocation = task.getResult();
                            locationResponse.onLocationResponse(mLastKnownLocation);
                        }else{
                            Toast.makeText(app, "Hmm, looks like the Get Location task dropped the ball. Maybe look into that...?", Toast.LENGTH_LONG).show();
                        }
                    }
                });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}


