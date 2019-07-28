package com.example.rharper.foundsound;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

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
    public static String latLngToString (LatLng latLng) {
        String latString = latLng.latitude + "," + latLng.longitude;
        return latLng == null ? null : latString;
    }

    @android.arch.persistence.room.TypeConverter
    public static LatLng stringToLatlng (String string) {
        if (string == null) { return null; }

        String[] array = string.split( ",");
        LatLng result = new LatLng(Double.parseDouble(array[0]), Double.parseDouble(array[1]));

        return result;
    }

}
