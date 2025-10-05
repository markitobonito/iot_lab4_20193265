package com.example.myapplication;

import java.util.List;

public class FutureResponse {
    private Location location;
    private ForecastResponse.ForecastData forecast;
    public Location getLocation() { return location; }
    public ForecastResponse.ForecastData getForecast() { return forecast; }
}
