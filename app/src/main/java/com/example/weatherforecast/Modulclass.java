package com.example.weatherforecast;

public class Modulclass {

    private String name;
    private String date;
    private  String  mintemperature;
    private String maxtemperature;

    public Modulclass(String name,String dt_txt, String temp_min, String temp_max)
    {
        this.name=name;
        this.date=dt_txt;
        this.mintemperature=temp_min;
        this.maxtemperature=temp_max;
    }

    public String getDate() {
        return date;
    }

    public String getMintemperature() {
        return mintemperature;
    }

    public String getMaxtemperature() {
        return maxtemperature;
    }

    public String getName() {
        return name;
    }

}
