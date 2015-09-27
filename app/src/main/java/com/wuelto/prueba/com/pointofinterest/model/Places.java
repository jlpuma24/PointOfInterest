package com.wuelto.prueba.com.pointofinterest.model;

/**
 * Created by grodriguez on 26/09/2015.
 * This class is created to define the model where the data from the web service will be save.
 */
public class Places {

    private String name;
    private String lon;
    private String lat;
    private String description;
    private String index;

    // Public Constructor to create the object
    public Places(String name, String lat, String lon,  String description, String index) {

        this.setName(name);
        this.setLon(lon);
        this.setLat(lat);
        this.setDescription(description);
        this.setIndex(index);

    }

    public Places (String name, String lat, String lon, String index) {

        this.setName(name);
        this.setLon(lon);
        this.setLat(lat);
        this.setIndex(index);

    }

    // Get and set for all the values.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

}
