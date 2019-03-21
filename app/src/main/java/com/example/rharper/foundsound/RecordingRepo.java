package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.Location;
import android.os.AsyncTask;

import java.util.Date;
import java.util.List;

public class RecordingRepo implements LocationResponseCallback{
    private RecordingDAO recordingDAO;
    private LiveData<List<Recording>> allRecordings;
    public MutableLiveData<Location> locationLiveData;
    private Application app;
    private LocationService locationService;

    private AudioRecorder recorder;

    private String currentRecordingName;

    RecordingRepo(Application application){
        app = application;
        RecordingDatabase db = RecordingDatabase.getDatabase(application);
        recordingDAO = db.recordingDAO();
        allRecordings = recordingDAO.getAllRecordings();
        locationService = new LocationService(app, this);

        locationLiveData = new MutableLiveData<>();
    }

    LiveData<List<Recording>> getAllRecordings() {
        return allRecordings;
    }

    public MutableLiveData<Location> getLocation(){
        return locationLiveData;
    }

    public void startNewRecording(){
        locationService.getDeviceLocation();
        currentRecordingName = generateName();
        recorder = new AudioRecorder(currentRecordingName, app.getFilesDir());
        recorder.startRecording();
    }

    public void stopStopNewRecording(){
        //Stop the current recorder, store the recording into the DB.
        recorder.stopRecording();

        insertNewRecordingIntoDB(new Recording(new Date(), "User name", currentRecordingName, locationLiveData.getValue()));
    }

    public void insertNewRecordingIntoDB(Recording newRecording) {
        new insertAsyncTask(recordingDAO).execute(newRecording);
    }

    private static class insertAsyncTask extends AsyncTask<Recording, Void, Void> {
        private RecordingDAO mAsyncTaskDao;
        insertAsyncTask(RecordingDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Recording... recordings) {
            mAsyncTaskDao.insertRecording(recordings);
            return null;
        }
    }

    public void updateLocation(){
        locationService.getDeviceLocation();
    }

    @Override
    public void onLocationResponse(Location location) {
        locationLiveData.setValue(location);
    }

    private String generateName(){
        return new Date().toString();
    }

}

//TODO: Figure out the order of operations for creating a new recording with live location

//Get location first
//Pass location to recorder
