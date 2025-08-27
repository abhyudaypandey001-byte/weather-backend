package com.Weather.WeatherAnalysis.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


public class Summary {

    private Double averageTemperature;
    private Double averageHumidity;
    private Double averageWindSpeed;
    private String location;
    //private String weatherCondition; // optional: maybe most common condition?

    public Summary(Double averageHumidity, Double averageTemperature, Double averageWindSpeed, String location) {
        this.averageHumidity = averageHumidity;
        this.averageTemperature = averageTemperature;
        this.averageWindSpeed = averageWindSpeed;
        this.location = location;
    }

    public Double getAverageHumidity() {
        return averageHumidity;
    }

    public void setAverageHumidity(Double averageHumidity) {
        this.averageHumidity = averageHumidity;
    }

    public Double getAverageTemperature() {
        return averageTemperature;
    }

    public void setAverageTemperature(Double averageTemperature) {
        this.averageTemperature = averageTemperature;
    }

    public Double getAverageWindSpeed() {
        return averageWindSpeed;
    }

    public void setAverageWindSpeed(Double averageWindSpeed) {
        this.averageWindSpeed = averageWindSpeed;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


}
