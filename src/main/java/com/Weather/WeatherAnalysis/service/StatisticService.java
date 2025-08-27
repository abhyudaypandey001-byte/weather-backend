package com.Weather.WeatherAnalysis.service;

import com.Weather.WeatherAnalysis.core.WeatherRecord;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticService {


    public Double averageTemperature(List<WeatherRecord> weatherRecordList) {
        double sum = 0.0;
        for (WeatherRecord weatherRecord : weatherRecordList) {
            double temp = weatherRecord.getTemperatureC();
            sum += temp;
        }
        double average = sum / weatherRecordList.size();
        return average;


    }
    public Double averageHumidity(List<WeatherRecord> weatherRecordList) {
        double sum = 0.0;
        for (WeatherRecord weatherRecord : weatherRecordList) {
            double temp = weatherRecord.getHumidity();
            sum += temp;
        }
        double average = sum / weatherRecordList.size();
        return average;

    }
    public Double averageWindSpeed(List<WeatherRecord> weatherRecordList) {
        double sum = 0.0;
        for (WeatherRecord weatherRecord : weatherRecordList) {
            double temp = weatherRecord.getWindSpeedMs();
            sum += temp;
        }
        double average = sum / weatherRecordList.size();
        return average;

    }

}
