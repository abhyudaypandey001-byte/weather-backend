package com.Weather.WeatherAnalysis.service;

import com.Weather.WeatherAnalysis.Enums.Cities;
import com.Weather.WeatherAnalysis.Enums.WeatherCondition;
import com.Weather.WeatherAnalysis.core.WeatherRecord;
import com.Weather.WeatherAnalysis.repository.WeatherRepository;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;

@Service
public class WeatherSimulationService {

    private final WeatherService weatherService;
    private final ThreadPoolTaskScheduler scheduler;
    private ScheduledFuture<?> future;
    private  final WeatherRepository repo;

    private final Random random = new Random();

    public WeatherSimulationService(WeatherService weatherService, WeatherRepository repo) {
        this.weatherService = weatherService;
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setPoolSize(1);
        this.scheduler.initialize();
        this.repo = repo;
    }

    // Start simulation
    public String startSimulation() {
        if (future != null && !future.isCancelled()) {
            return "Simulation already running";
        }

        future = scheduler.scheduleAtFixedRate(this::insertRandomWeather, 10_000); // every 10 sec
        return "Simulation started";
    }

    // Stop simulation
    public String stopSimulation() {
        if (future != null) {
            future.cancel(true);
            future = null;
            return "Simulation stopped";
        }
        return "No simulation running";
    }

    // Random weather generator
    private void insertRandomWeather() {
        WeatherRecord record = new WeatherRecord();

        record.setLocation(randomLocation());
        record.setTimestamp(LocalDate.now());

        // Generate with 2 decimal places
        double temperature = BigDecimal.valueOf(10 + random.nextDouble() * 20)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        double humidity = BigDecimal.valueOf(40 + random.nextDouble() * 40)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        double windSpeed = BigDecimal.valueOf(random.nextDouble() * 10)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();

        record.setTemperatureC(temperature);
        record.setHumidity(humidity);
        record.setWindSpeedMs(windSpeed);
        record.setWeatherCondition(randomWeatherCondition());

        weatherService.addRecord(List.of(record));
        System.out.println("Inserted simulated weather: " + record.getTemperatureC());
        repo.save(record);
    }
    private String randomWeatherCondition() {
        WeatherCondition condition = WeatherCondition.values()[random.nextInt(WeatherCondition.values().length)];
        return condition.name();
    }
    public String randomLocation(){
        Cities cities = Cities.values()[random.nextInt(Cities.values().length)];
        return cities.name();
    }


}