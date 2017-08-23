package com.allen.onlyweather.weather.entity;

/**
 * Created by Allen.
 */

public class LifeIndex {
    public String name;
    public String brf;
    public String txt;

    public LifeIndex(String name, String brf, String txt) {
        this.name = name;
        this.brf = brf;
        this.txt = txt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrf() {
        return brf;
    }

    public void setBrf(String brf) {
        this.brf = brf;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
