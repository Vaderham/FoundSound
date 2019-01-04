package com.example.rharper.foundsound;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Recording {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String name;
    private String locationData;

    public Recording(Date date, String name, String locationData) {
        this.date = date;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationData() {
        return locationData;
    }

    public void setLocationData(String locationData) {
        this.locationData = locationData;
    }
}
