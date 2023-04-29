package com.marful.exampleparsedrive.Entities;

import java.io.Serializable;

public class ChargingPoint implements Serializable {

    private Double id;
    private String municipi;
    private String provincia;
    private Double latitude;
    private Double longitude;
    private Float distance;

    public ChargingPoint(Double id, String municipi, String provincia, Double latitude, Double longitude, Float distance) {
        this.id = id;
        this.municipi = municipi;
        this.provincia = provincia;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getMunicipi() {
        return municipi;
    }

    public void setMunicipi(String municipi) {
        this.municipi = municipi;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }
}
