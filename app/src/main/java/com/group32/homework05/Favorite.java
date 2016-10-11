/*
 Homwork 5
 Favourite
 Akarsh Gupta     - 800969888
 Ahmet Gencoglu   - 800982227
*/
package com.group32.homework05;

public class Favorite {

    // Simple class to hold Favorites
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
