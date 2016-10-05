package com.group32.homework05;

import java.io.Serializable;

public class Weather implements Serializable {

    private String time, temperature, dewpoint, clouds,
        iconURL, windSpeed, windDirection, climateType,
        humidity, maximumTemperature, minimumTemperature, pressure, feelsLike;

    public String getFeelsLike() {
        return feelsLike;
    }

    public Weather(String time, String temperature, String dewpoint, String clouds, String iconURL, String windSpeed, String windDirection, String climateType, String humidity, String feelsLike, String maximumTemperature, String minimumTemperature, String pressure) {
        this.time = time;
        this.temperature = temperature;
        this.dewpoint = dewpoint;

        this.clouds = clouds;
        this.iconURL = iconURL;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.climateType = climateType;
        this.humidity = humidity;
        this.maximumTemperature = maximumTemperature;
        this.minimumTemperature = minimumTemperature;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
    }

    public String getTime() {
        return time;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getDewpoint() {
        return dewpoint;
    }

    public String getClouds() {
        return clouds;
    }

    public String getIconURL() {
        return iconURL;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public String getWindDirection() {
        return windDirection;
    }

    public String getClimateType() {
        return climateType;
    }

    public String getHumidity() {
        return humidity;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public String getPressure() {
        return pressure;
    }

    public String getWind(){
        return windSpeed + windDirection;
    }
}


