package com.example.rharper.foundsound;

import android.location.Location;

import java.util.Date;

public class TypeConverter {

    @android.arch.persistence.room.TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null ? null : new Date(timestamp);
    }

    @android.arch.persistence.room.TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @android.arch.persistence.room.TypeConverter
    public static String locationToString (Location location){
        return location == null ? null : location.toString();
    }

    @android.arch.persistence.room.TypeConverter
    public static Location stringToLocation (String string){
        return string == null ? null : new Location(string);
    }

}
