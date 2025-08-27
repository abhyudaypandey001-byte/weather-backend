package com.Weather.WeatherAnalysis.repository;

import com.Weather.WeatherAnalysis.core.WeatherRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeatherRepository extends JpaRepository<WeatherRecord , Long> {
    List<WeatherRecord> findByLocation(String location);
}
