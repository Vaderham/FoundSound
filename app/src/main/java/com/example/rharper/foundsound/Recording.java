package com.example.rharper.foundsound;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.location.Location;

import java.io.File;
import java.util.Date;

@Entity
public class Recording {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private String name;
    private String fileName;
    private Location locationData;

    public Recording(Date date, String name, String fileName, Location locationData) {
        this.date = date;
        this.name = name;
        this.fileName = fileName;
        this.locationData = locationData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String name) {
        this.fileName = name;
    }

    public Location getLocationData() {
        return locationData;
    }

    public void setLocationData(Location locationData) {
        this.locationData = locationData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
