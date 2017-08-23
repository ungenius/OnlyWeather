package com.allen.onlyweather.weather.entity;

/**
 * Created by Allen.
 */

public class DailyForecast {

    /*
     * "astro": {
     *                     "mr": "15:32",
     *                     "ms": "02:03",
     *                     "sr": "05:56",
     *                     "ss": "19:03"
     *                 },
     *                 "cond": {
     *                     "code_d": "300",
     *                     "code_n": "300",
     *                     "txt_d": "阵雨",
     *                     "txt_n": "阵雨"
     *                 },
     *                 "date": "2017-08-03",
     *                 "hum": "70",
     *                 "pcpn": "8.5",
     *                 "pop": "100",
     *                 "pres": "1001",
     *                 "tmp": {
     *                     "max": "31",
     *                     "min": "26"
     *                 },
     *                 "uv": "12",
     *                 "vis": "13",
     *                 "wind": {
     *                     "deg": "0",
     *                     "dir": "无持续风向",
     *                     "sc": "微风",
     *                     "spd": "8"
     *                 }
     */

    public Astro astro;
    public DailyCond cond;
    public Tmp tmp;
    public Wind wind;
    public String date;
    public String hum;
    public String pcpn;
    public String pop;
    public String pres;
    public String uv;
    public String vis;


    public class Astro {

        public String mr;
        public String ms;
        public String sr;
        public String ss;

    }
    public class DailyCond {

        public String code_d;
        public String code_n;
        public String txt_d;
        public String txt_n;

    }
    public class Tmp {

        public String max;
        public String min;

    }



}
