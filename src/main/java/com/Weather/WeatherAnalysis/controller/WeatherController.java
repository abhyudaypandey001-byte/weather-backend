package com.Weather.WeatherAnalysis.controller;

import com.Weather.WeatherAnalysis.core.CsvWeatherReader;
import com.Weather.WeatherAnalysis.core.Summary;
import com.Weather.WeatherAnalysis.core.WeatherRecord;
import com.Weather.WeatherAnalysis.service.WeatherService;
import com.Weather.WeatherAnalysis.service.WeatherSimulationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@CrossOrigin
@RequestMapping("/weather")
@RestController
public class WeatherController {
    @Autowired
    private CsvWeatherReader csvWeatherReader;


    private final WeatherService service;
    private  final WeatherSimulationService weatherSimulationService;

    public WeatherController(WeatherService service,WeatherSimulationService weatherSimulationService) {
        this.weatherSimulationService=weatherSimulationService;
        this.service = service;
    }

    @PostMapping("/upload/csv")
    public ResponseEntity<String> uploadWeatherData(@RequestParam("file") MultipartFile file) {
        try {
            service.saveCsv(file);  // Parse + save
            return ResponseEntity.ok("CSV uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Upload failed: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public List<WeatherRecord> uploadFileJson(@RequestBody List<WeatherRecord> record) throws Exception {
        return service.addRecord(record);
    }

    @GetMapping("/records")
    public List<WeatherRecord> getRecords() {
        return service.getAllRecords();
    }

    @GetMapping("/records/{id}")
    public ResponseEntity<WeatherRecord> getRecordById(@PathVariable Long id) {
        if (id != null) {
            return new ResponseEntity<>(service.getRecordById(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/records/location/{location}")
    public ResponseEntity<List<WeatherRecord>> getRecordsByLocation(@PathVariable String location) {
        if (location != null && !location.isEmpty()) {
            return new ResponseEntity<>(service.getRecordsByLocation(location), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/records/averageTemp/{location}")
    public ResponseEntity<Double> getAverageTemperature(@PathVariable String location) {
        if (location != null && !location.isEmpty()) {
            return new ResponseEntity<>(service.averageTemperature(location), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/records/stats/{location}")
    public ResponseEntity<Summary> getStats(@PathVariable String location) {
        if (location != null && !location.isEmpty()) {
            return new ResponseEntity<>(service.getSummary(location), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/records/filter")
    public ResponseEntity<List<WeatherRecord>> getFilteredRecords(
            @RequestParam String location,
            @RequestParam(required = false) Double startTemp,
            @RequestParam(required = false) Double endTemp,
            @RequestParam(required = false) String from,
            @RequestParam(required = false) String to) {

        LocalDate fromDate = from != null ? LocalDate.parse(from) : null;
        LocalDate toDate = to != null ? LocalDate.parse(to) : null;

        List<WeatherRecord> filtered = service.getRecordsByFilters(location, startTemp, endTemp, fromDate, toDate);

        if (filtered.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(filtered);
    }
    @PostMapping("/simulation/start")
    public String startSimulation() {
        ; // Insert one immediately
        return weatherSimulationService.startSimulation();
    }

    @PostMapping("/simulation/stop")
    public String stopSimulation() {
        return weatherSimulationService.stopSimulation();
    }

    @DeleteMapping("/records/delete/{id}")
    public ResponseEntity<String> deleteWeatherRecordById(@PathVariable Long id){
        service.deleteWeatherRecordById(id);
        return new ResponseEntity<>("Deleted: "+id,HttpStatus.OK);
    }




}


