package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;

import java.util.List;

public class MapRecordViewModel extends AndroidViewModel {

    private RecordingRepo repository;
    private LiveData<List<Recording>> allRecordings;
    private MutableLiveData<Location> locationMutableLiveData;
    private boolean recordingState;

    public MapRecordViewModel(Application application){
        super(application);

        locationMutableLiveData = new MutableLiveData<>();
        repository = new RecordingRepo(application, locationMutableLiveData);

        allRecordings = repository.getAllRecordings();

        repository.getLocation();
    }

    public void newRecording(){

        if(!recordingState){
            repository.startNewRecording();
            recordingState = true;
        }else{
            repository.stopStopNewRecording();
            recordingState = false;
        }
    }

    public LiveData<List<Recording>> getRecordings() {
        return allRecordings;
    }

    public MutableLiveData<Location> getCurrentLocation() {
        if (locationMutableLiveData == null) {
            locationMutableLiveData = new MutableLiveData<>();
        }
        return locationMutableLiveData;
    }

}