package com.allen.onlyweather.weather.entity;

/**
 * Created by Allen.
 */

public class AQI {

    /**
     * "aqi": {
     *     "city": {
     *         "aqi": "21",
     *         "co": "1",
     *         "no2": "33",
     *         "o3": "24",
     *         "pm10": "21",
     *         "pm25": "14",
     *         "qlty": "优",
     *         "so2": "6"
     *     }
     * }
     */

    public AQICity city;

    public class AQICity {
        public String aqi;
        public String co;
        public String no2;
        public String o3;
        public String pm10;
        public String pm25;
        public String qlty;
        public String so2;
    }
}
