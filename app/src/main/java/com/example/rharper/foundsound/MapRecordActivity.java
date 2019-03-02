package com.example.rharper.foundsound;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;

/*TODO: Observe location livedata and update map when it changes. (How will this be updated as user moves?)
    For now, just update location when user begins recording.
*/

public class MapRecordActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION};
    private CircleOptions circleOptions = new CircleOptions();
    private boolean permissionToRecordAccepted = false;
    private boolean mLocationPermissionGranted = false;
    private ImageButton recordButton;
    private MapRecordViewModel mMapRecordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_record);

        recordButton = findViewById(R.id.record);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mMapRecordViewModel = ViewModelProviders.of(this).get(MapRecordViewModel.class);
        mMapRecordViewModel.getRecordings().observe(this, new Observer<List<Recording>>() {
                    @Override
                    public void onChanged(@Nullable List<Recording> recordings){
                        for (int i = 0; i < recordings.size(); i++){
                            Log.v("DB: ", recordings.get(i).getFileName());
                        }
                    }
                });

        mMapRecordViewModel.getCurrentLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, DEFAULT_ZOOM));
                circleOptions.center(locationLatLng);
                mMap.addCircle(circleOptions);
            }
        });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapRecordViewModel.newRecording();
            }
        });

//        getLocationPermission();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if(grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                    mLocationPermissionGranted = true;
                }
                break;
        }
        if (!permissionToRecordAccepted) finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

}