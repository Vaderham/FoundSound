package com.example.rharper.foundsound;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class RecordingRepo{
    private RecordingDAO recordingDAO;
    private LiveData<List<Recording>> allRecordings;

    

    RecordingRepo(Application application){
        RecordingDatabase db = RecordingDatabase.getDatabase(application);
        recordingDAO = db.recordingDAO();

        allRecordings = recordingDAO.getAllRecordings();
    }

    LiveData<List<Recording>> getAllRecordings() {
        return allRecordings;
    }

    public void insert (Recording recording) {
        new insertAsyncTask(recordingDAO).execute(recording);
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
