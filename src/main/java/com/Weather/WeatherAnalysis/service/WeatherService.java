package com.Weather.WeatherAnalysis.service;

import com.Weather.WeatherAnalysis.core.Summary;
import com.Weather.WeatherAnalysis.core.WeatherRecord;
import com.Weather.WeatherAnalysis.repository.WeatherRepository;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherService {

    private final WeatherRepository repo;




    private WeatherRecord record;

    private final StatisticService statisticService;


    public WeatherService(WeatherRepository repo, StatisticService statisticService) {
        this.repo = repo;
        this.statisticService = statisticService;

    }


    public List<WeatherRecord> getAllRecords() {
        return repo.findAll();
    }

    public List<WeatherRecord> addRecord(List<WeatherRecord> records) {
       for(WeatherRecord record: records){
           if (record.getTimestamp() == null || record.getLocation() == null) {
               throw new IllegalArgumentException("Date and Location cannot be null");
           }
       }
        return repo.saveAll(records);

    }
    public void saveCsv(MultipartFile file) throws IOException {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);

        CsvParser parser = new CsvParser(settings);
        List<Record> records = parser.parseAllRecords(new InputStreamReader(file.getInputStream()));

        List<WeatherRecord> weatherRecords = new ArrayList<>();

        for (Record record : records) {
            WeatherRecord wr = new WeatherRecord();

            wr.setId(null); // Let JPA handle ID
            wr.setTimestamp(LocalDate.parse(record.getString("timestamp")));
            wr.setLocation(record.getString("location"));
            wr.setTemperatureC(Double.parseDouble(record.getString("temperatureC")));
            wr.setHumidity(Double.parseDouble(record.getString("humidity")));
            wr.setWindSpeedMs(Double.parseDouble(record.getString("windSpeedMs")));
            wr.setWeatherCondition(record.getString("weatherCondition"));

            weatherRecords.add(wr);
        }

        repo.saveAll(weatherRecords);
    }

    public WeatherRecord getRecordById(Long id) {
        return repo.findById(id).orElse(null);
    }

    public List<WeatherRecord> getRecordsByLocation(String location) {
        return repo.findByLocation(location);
    }

    public Double averageTemperature(String location) {
        List<WeatherRecord> records = repo.findByLocation(location);
        return statisticService.averageTemperature(records);
    }
    public Summary getSummary(String location) {

        List<WeatherRecord> records = repo.findByLocation(location);
        if (records.isEmpty()) {
            return null; // or throw an exception
        }
        Double avgTemp = statisticService.averageTemperature(records);
        Double avgHumidity = statisticService.averageHumidity(records);
        Double avgWindSpeed = statisticService.averageWindSpeed(records);

        return new Summary(avgHumidity, avgTemp, avgWindSpeed,location);
    }


    public List<WeatherRecord> getRecordsByFilters(String location, Double startTemp, Double endTemp,
                                                   LocalDate fromDate, LocalDate toDate) {
        List<WeatherRecord> records = repo.findByLocation(location);

        return records.stream()
                .filter(r -> (startTemp == null || r.getTemperatureC() >= startTemp) &&
                        (endTemp == null || r.getTemperatureC() <= endTemp) &&
                        (fromDate == null || !r.getTimestamp().isBefore(fromDate)) &&
                        (toDate == null || !r.getTimestamp().isAfter(toDate)))
                .collect(Collectors.toList());
    }


    public void deleteWeatherRecordById(Long id) {
        repo.deleteById(id);
    }
}
