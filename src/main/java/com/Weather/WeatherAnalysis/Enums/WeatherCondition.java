package com.Weather.WeatherAnalysis.Enums;

public enum WeatherCondition {
    WINTER_STORM("Winter storm"),
    THUNDERSTORM("Thunderstorm"),
    TORNADO("Tornado"),
    RAIN("Rain"),
    TEMPERATURE("Temperature"),
    HURRICANE("Hurricane"),
    SNOWY("Snowy"),
    BLIZZARD("Blizzard"),
    CLOUDY("Cloudy"),
    FOG("Fog"),
    PRECIPITATION("Precipitation"),
    DRIZZLE("Drizzle"),
    TROPICAL_CYCLONE("Tropical cyclone"),
    SLEET("Sleet"),
    SUNNY("Sunny"),
    WINDY("Windy"),
    COLD("Cold"),
    HUMIDITY("Humidity"),
    STORM("Storm"),
    WIND("Wind"),
    HAIL("Hail"),
    PRESSURE("Pressure"),
    DUST_STORM("Dust storm"),
    CLEAR("Sunny/Clear");

    private final String label;

    WeatherCondition(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
