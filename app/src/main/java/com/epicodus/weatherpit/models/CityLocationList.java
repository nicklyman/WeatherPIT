package com.epicodus.weatherpit.models;


public class CityLocationList {
    String cityStateName;
    double cityLatitude;
    double cityLongitude;

    public CityLocationList() {}

    public CityLocationList(String cityStateName, double cityLatitude, double cityLongitude) {
        this.cityStateName = cityStateName;
        this.cityLatitude = cityLatitude;
        this.cityLongitude = cityLongitude;
    }

    public String getCityStateName() {
        return cityStateName;
    }
    public double getCityLatitude() {
        return cityLatitude;
    }
    public double getCityLongitude() {
        return cityLongitude;
    }
}
