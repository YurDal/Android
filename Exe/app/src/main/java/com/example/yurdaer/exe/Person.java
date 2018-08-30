package com.example.yurdaer.exe;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by YURDAER on 2017-11-01.
 */

public class Person {
    private LatLng latLng;
    private String name;


    public Person(String name, LatLng latLng) {
        this.name = name;
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }


    public String getName() {
        return name;
    }




}
