package com.example.rharper.foundsound;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RecordingDAO {
    @Insert
    void insertRecording(Recording... recordings);

    @Query("SELECT * FROM Recording")
    LiveData<List<Recording>> getAllRecordings();
}