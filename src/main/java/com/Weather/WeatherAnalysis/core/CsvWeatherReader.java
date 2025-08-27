package com.Weather.WeatherAnalysis.core;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvWeatherReader implements Reader {
    @Override
    public List<WeatherRecord> read(String filePath) throws Exception {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        CsvParser parser = new CsvParser(settings);
        List<String[]> rows = parser.parseAll(new File(filePath));
        List<WeatherRecord> rec = new ArrayList<>();
        for(String[] row : rows){
            WeatherRecord record = new WeatherRecord();
            record.setId(Long.parseLong(row[0]));
            record.setTimestamp(LocalDate.parse(row[1]));
            record.setLocation(row[2]);
            record.setTemperatureC(Double.parseDouble(row[3]));
            record.setHumidity(row[4].isEmpty() ? null : Double.parseDouble(row[3]));
            record.setWindSpeedMs(row[5].isEmpty() ? null : Double.parseDouble(row[4]));
            record.setWeatherCondition(row[6]);
            rec.add(record);
        }
        return rec;
    }
}
