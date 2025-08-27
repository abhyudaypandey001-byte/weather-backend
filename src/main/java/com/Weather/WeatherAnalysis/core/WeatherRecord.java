package com.Weather.WeatherAnalysis.core;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "weather_records")
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate timestamp;
    private String location;
    private double temperatureC;
    private Double humidity;
    private Double windSpeedMs;
    private String weatherCondition;

    public WeatherRecord(LocalDate timestamp, String location, double temperatureC, Double humidity, Double windSpeedMs, String weatherCondition,Long id) {
        this.timestamp = timestamp;
        this.location = location;
        this.temperatureC = temperatureC;
        this.humidity = humidity;
        this.windSpeedMs = windSpeedMs;
        this.weatherCondition = weatherCondition;
    }
    public WeatherRecord(){

    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getWeatherCondition() {
        return weatherCondition;
    }

    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public Double getWindSpeedMs() {
        return windSpeedMs;
    }

    public void setWindSpeedMs(Double windSpeedMs) {
        this.windSpeedMs = windSpeedMs;
    }
    @Override
    public String toString() {
        return String.format("%s  %s  %.1f°C  %.1f%%  %s",
                timestamp, location, temperatureC, humidity, weatherCondition);
    }
}