package com.example.myapplication;

public class Forecast {
    private String date;
    private String maxTemp;
    private String minTemp;
    private String condition;
    // Agrega más atributos si lo necesitas

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getMaxTemp() { return maxTemp; }
    public void setMaxTemp(String maxTemp) { this.maxTemp = maxTemp; }

    public String getMinTemp() { return minTemp; }
    public void setMinTemp(String minTemp) { this.minTemp = minTemp; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }
}
