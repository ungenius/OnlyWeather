package com.allen.onlyweather.weather.entity;

/**
 * Created by Allen.
 */

public class Basic {

    /**
     * "basic": {
     *      "city": "深圳",
     *      "cnty": "中国",
     *      "id": "CN101280601",
     *      "lat": "22.54700089",
     *      "lon": "114.08594513",
     *      "update": {
     *      "loc": "2017-08-03 23:55",
     *      "utc": "2017-08-03 15:55"
     *     }
     *  }
     *
     */

    public String city;
    public String cnty;
    public String id;
    public String lat;
    public String lon;
    public Update update;


    public class Update {
        public String loc;
        public String utc;
    }

}
