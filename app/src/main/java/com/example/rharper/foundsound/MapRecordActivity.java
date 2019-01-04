package com.example.rharper.foundsound;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Console;
import java.io.File;
import java.security.Provider;
import java.util.List;

public class MapRecordActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private String [] permissions = {Manifest.permission.RECORD_AUDIO};
    private boolean permissionToRecordAccepted = false;
    private ImageButton recordButton;
    private Button stopButton;
    private MapRecordViewModel mMapRecordViewModel;

    private AudioRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_record);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        mMapRecordViewModel = ViewModelProviders.of(this).get(MapRecordViewModel.class);

        mMapRecordViewModel.getRecordings().observe(this, new Observer<List<Recording>>() {
                    @Override
                    public void onChanged(@Nullable List<Recording> recordings) {

//                        if (recordings.size() < 1){
//                            String latest = Integer.toString(recordings.get(recordings.size() - 1).getId());
//                            Toast.makeText(MapRecordActivity.this, latest, Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(MapRecordActivity.this, "Hmm, looks like it's empty. Length: " + recordings.size() , Toast.LENGTH_SHORT).show();
//                        }

                    }
                });

        recordButton = findViewById(R.id.record);
        stopButton = findViewById(R.id.stop);

        final File saveLocation = getFilesDir();
        recorder = new AudioRecorder("Get location", saveLocation); //TODO: Get this location from Google maps API and update file name.

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recorder.startRecording();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recording newRecording = recorder.stopRecording();
                Toast.makeText(MapRecordActivity.this, newRecording.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted ) finish();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}


/*
Update map + data
Record audio / Stop recording
 */