package com.example.weatherforecast;



public class Data {
    private final String date;
    private final String image;
    String temp;



    public Data(String date, String image,String temp) {
        this.date = date;
        this.image=image;
        this.temp=temp;
    }

    public String getTemp() {
        return temp;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

}
