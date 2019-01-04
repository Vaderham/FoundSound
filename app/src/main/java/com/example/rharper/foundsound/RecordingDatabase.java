package com.example.rharper.foundsound;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;


@Database(entities = {Recording.class}, version = 1, exportSchema = false)
@TypeConverters({TypeConverter.class})
public abstract class RecordingDatabase extends RoomDatabase {

    public abstract RecordingDAO recordingDAO();

    private static volatile RecordingDatabase INSTANCE;

    public static RecordingDatabase getDatabase(final Context context) {

        if (INSTANCE == null) {
            synchronized (RecordingDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(), RecordingDatabase.class, "recordings_db")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }

}
