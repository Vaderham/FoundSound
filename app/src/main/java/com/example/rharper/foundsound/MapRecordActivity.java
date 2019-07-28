package com.example.rharper.foundsound;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/*TODO:
- Give user a way to name the recording.
- When user taps a recording on map, show player and animate so that spot is centered
- Play back recordings in a media player
- Allow to delete recordings
 */

public class MapRecordActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, OnRecyclerItemClickListener {

    private GoogleMap mMap;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION};
    private boolean permissionToRecordAccepted = false;
    private boolean mLocationPermissionGranted = false;
    private ImageButton recordButton;
    private MapRecordViewModel mMapRecordViewModel;
    private BottomSheetAdapter listAdapter;
    private BottomSheetBehavior bottomSheetBehavior;

    private ArrayList<Recording> recordingList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_record);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        recordButton = findViewById(R.id.record);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mMapRecordViewModel = ViewModelProviders.of(this).get(MapRecordViewModel.class);

        mMapRecordViewModel.getCurrentLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                LatLng locationLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationLatLng, DEFAULT_ZOOM));
            }
        });

        mMapRecordViewModel.getRecordings().observe(this, new Observer<List<Recording>>() {
                    @Override
                    public void onChanged(@Nullable List<Recording> recordings){
                        recordingList.clear();
                        recordingList.addAll(recordings);
                        addRecordingsToMap();
                        listAdapter.notifyDataSetChanged();
                    }
                });

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapRecordViewModel.newRecording();
                if (!mMapRecordViewModel.getRecordingState()){
                    Toast.makeText(MapRecordActivity.this, "Fin", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(MapRecordActivity.this, "Start", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom_sheet_layout));

        RecyclerView bottomSheetRecycler = findViewById(R.id.bottom_sheet_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        bottomSheetRecycler.setLayoutManager(layoutManager);
        listAdapter = new BottomSheetAdapter(recordingList, this);
        bottomSheetRecycler.setAdapter(listAdapter);

        addRecordingsToMap();
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
        try{
            mMap.setMyLocationEnabled(true);
        }catch(SecurityException e){
            Toast.makeText(this, "Yeah, nah. You got a: " + e, Toast.LENGTH_SHORT).show();
        }
        mMap.setOnMyLocationButtonClickListener(this);
    }

    private void addRecordingsToMap(){
        for (int i = 0; i < recordingList.size(); i++){
            mMap.addMarker(new MarkerOptions().position(recordingList.get(i).getLocationData()).title(recordingList.get(i).getLocationData().toString()));
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onItemClick(Recording recording) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(recording.getLocationData(), DEFAULT_ZOOM));
    }
}