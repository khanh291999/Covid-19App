package com.example.covid_19.api;

import com.example.covid_19.HelperClasses.NewsHelperClass;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsHelperClass> getNew(

            @Query("country") String country,
            @Query("q") String q,
            @Query("apiKey") String apiKey
    );

    @GET("everything")
    Call<NewsHelperClass> getNewsSearch(

            @Query("w") String keyword,
            @Query("Language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey

    );
}
