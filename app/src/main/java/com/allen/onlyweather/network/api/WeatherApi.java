package com.allen.onlyweather.network.api;

import com.allen.onlyweather.weather.entity.HeWeather;
import com.allen.onlyweather.weather.entity.Weather;

import java.security.Key;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Allen.
 */

public interface WeatherApi {

//    String BASE_URL = "http://172.20.10.4:80/";
//    String BASE_URL = "http://192.168.3.13:80";
//    String BASE_URL = "http://192.168.3.29:80";
    String BASE_URL = "https://free-api.heweather.com/v5/";
//    String BASE_URL = "http://10.0.0.207:80/";
//    String BASE_URL = "http://116.25.157.55:80/";


    //    @GET("{cityId}")
//    Call<HeWeather> getWeather(@Path("cityId") String cityId);
    @GET("weather")
    Call<HeWeather> getWeather(@Query("city") String cityId, @Query("key") String Key);



}
