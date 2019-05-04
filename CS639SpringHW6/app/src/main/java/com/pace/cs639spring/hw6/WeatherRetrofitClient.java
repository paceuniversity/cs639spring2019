package com.pace.cs639spring.hw6;


import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

//client that is used for connecting to Weather API.
public class WeatherRetrofitClient {

    //internal retrofit instance
    Retrofit mRetrofit;


    private final String API_KEY = "YOUR_API_KEY";
    private final String BASE_URL = "http://api.worldweatheronline.com/premium/v1/";
    private final String FORMAT = "json";
    private static WeatherRetrofitClient ourInstance = new WeatherRetrofitClient();

    public static WeatherRetrofitClient getInstance() {
        return ourInstance;
    }

    private WeatherService mWeatherService;

    private WeatherRetrofitClient() {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        mWeatherService = mRetrofit.create(WeatherService.class);
    }


    //let's say I called this with
    //fetchWeather(40.87, -71.3244, "2019-05-06", new Callback()...)
    public void fetchWeather(double lat, double lon, String date, Callback<ResponseBody> callback) {
        String latLng = String.format(Locale.US, "%f,%f", lat, lon);
        mWeatherService.fetchWeather(1, latLng, date, FORMAT, API_KEY)
                .enqueue(callback);
    }

    private interface WeatherService {

        //GET http://api.worldweatheronline.com/premium/v1/weather.ashx?num_of_days=1&q=40.87,-71.3244&date=2019-05-06&format=json&key=YOUR_API_KEY
        @GET("weather.ashx")
        Call<ResponseBody> fetchWeather(@Query("num_of_days") int number, @Query("q") String latLng, @Query("date") String date,
                                        @Query("format") String format, @Query("key") String key);
    }

}
