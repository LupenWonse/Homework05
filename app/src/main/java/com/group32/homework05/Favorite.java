package com.group32.homework05;

/**
 * Created by ahmet on 05/10/2016.
 */

public class Favorite {

    private String temperature, city, state, updateDate;

    public Favorite(String temperature, String city, String state, String updateDate) {
        this.temperature = temperature;
        this.city = city;
        this.state = state;
        this.updateDate = updateDate;
    }

    public String getTemperature() {

        return temperature;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getUpdateDate() {
        return updateDate;
    }
}
