package com.example.covid_19.api;

import com.example.covid_19.HelperClasses.NewsHelperClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsHelperClass> getNew(

            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
}
