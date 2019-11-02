package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.location.Location;
import android.support.annotation.Nullable;

import java.util.List;

public class MapRecordViewModel extends AndroidViewModel {

    private RecordingRepo repository;
    private LiveData<List<Recording>> allRecordings;
    private MutableLiveData<Location> locationLiveData;
    private MutableLiveData<Boolean> nameCollection;
    private boolean recordingState;

    public MapRecordViewModel(Application application){
        super(application);

        repository = new RecordingRepo(application);
        allRecordings = repository.getAllRecordings();
        repository.updateLocation();
        locationLiveData = repository.getLocation();
        nameCollection = repository.getNameCollection();
    }

    public void newRecording(){
        if(!recordingState){
            repository.startNewRecording();
            recordingState = true;
        }else{
            repository.pauseNewRecording();
            recordingState = false;
        }
    }



    public LiveData<List<Recording>> getRecordings() {
        return allRecordings;
    }

    public MutableLiveData<Location> getCurrentLocation(){
        return locationLiveData;
    }

    public boolean getRecordingState(){
        return recordingState;
    }

    public MutableLiveData<Boolean> getNameCollection(){
        return nameCollection;
    }

    public void saveRecordingWithName(String name){
        repository.stopNewRecording(name);
    }
}