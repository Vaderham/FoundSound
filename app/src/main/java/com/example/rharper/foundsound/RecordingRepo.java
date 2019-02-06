package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.io.File;
import java.util.Date;
import java.util.List;

public class RecordingRepo{
    private RecordingDAO recordingDAO;
    private LiveData<List<Recording>> allRecordings;
    private Application app;

    private AudioRecorder recorder;

    RecordingRepo(Application application){
        app = application;
        RecordingDatabase db = RecordingDatabase.getDatabase(application);
        recordingDAO = db.recordingDAO();

        allRecordings = recordingDAO.getAllRecordings();
    }

    LiveData<List<Recording>> getAllRecordings() {
        return allRecordings;
    }

    public void insertNewRecordingIntoDB(Recording newRecording) {
        new insertAsyncTask(recordingDAO).execute(newRecording);

    }

    public void startNewRecording(){
        //Start a new recording and query Google Maps API for current location.
        recorder = new AudioRecorder("Name", app.getFilesDir());
        recorder.startRecording();

    }

    public void stopStopNewRecording(){
        //Stop the current recorder, store the recording into the DB.
        Recording recording = recorder.stopRecording();

        //
        Recording testRecording = new Recording(new Date(), "Test", "Stored here");
        //

        insertNewRecordingIntoDB(testRecording);
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

}
