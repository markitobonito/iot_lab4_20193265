package com.example.myapplication;

import java.util.List;

public class ForecastResponse {
    private Location location;
    private ForecastData forecast;

    public Location getLocation() { return location; }
    public ForecastData getForecast() { return forecast; }

    public static class ForecastData {
        private List<ForecastDay> forecastday;
        public List<ForecastDay> getForecastday() { return forecastday; }
    }

    public static class ForecastDay {
        private String date;
        private Day day;
        public String getDate() { return date; }
        public Day getDay() { return day; }
    }

    public static class Day {
        private float maxtemp_c;
        private float mintemp_c;
        private Condition condition;
        public float getMaxtemp_c() { return maxtemp_c; }
        public float getMintemp_c() { return mintemp_c; }
        public Condition getCondition() { return condition; }
    }

    public static class Condition {
        private String text;
        public String getText() { return text; }
    }
}
