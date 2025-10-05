package com.example.myapplication;

public class HourlyForecast {
    private String hour;
    private String temp;
    private String condition;
    private String humidity;
    private String rain;
    // Agrega más atributos si lo necesitas

    public String getHour() { return hour; }
    public void setHour(String hour) { this.hour = hour; }

    public String getTemp() { return temp; }
    public void setTemp(String temp) { this.temp = temp; }

    public String getCondition() { return condition; }
    public void setCondition(String condition) { this.condition = condition; }

    public String getHumidity() { return humidity; }
    public void setHumidity(String humidity) { this.humidity = humidity; }

    public String getRain() { return rain; }
    public void setRain(String rain) { this.rain = rain; }
}
