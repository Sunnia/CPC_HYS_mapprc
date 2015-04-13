package com.example.michael.mapprc;

/**
 * Created by michael on 13/04/15.
 */
public class Station {
    public String name;
    public String address;
    public Float latitude;
    public Float longtitude;

    public Station(){}

    public Station(String name, String addr, String lat, String longti){
        this.name=name;
        this.address=addr;
        this.latitude=Float.valueOf(lat);
        this.longtitude=Float.valueOf(longti);
    }

    public String getname() {
        return name;
    }

    public void setname(String id) {
        this.name = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String id) {
        this.address = id;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(String id) {
        this.latitude = Float.valueOf(id);
    }

    public Float getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String id) {
        this.longtitude = Float.valueOf(id);
    }








}
