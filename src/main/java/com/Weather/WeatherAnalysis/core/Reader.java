package com.Weather.WeatherAnalysis.core;

import java.util.List;

public interface Reader {
    List<WeatherRecord> read(String filePath) throws Exception;
}
