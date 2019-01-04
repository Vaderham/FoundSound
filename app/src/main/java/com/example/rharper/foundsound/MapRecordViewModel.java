package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MapRecordViewModel extends AndroidViewModel {

    private RecordingRepo repository;

    private LiveData<List<Recording>> allRecordings;

    public MapRecordViewModel(Application application){
        super(application);
        repository = new RecordingRepo(application);
        allRecordings = repository.getAllRecordings();
    }

    public void record(){

    }

    public LiveData<List<Recording>> getRecordings() {
        return allRecordings;
    }

}
