package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.covid_19.HelperClasses.Article;
import com.example.covid_19.HelperClasses.NewsApdapter;
import com.example.covid_19.HelperClasses.NewsHelperClass;
import com.example.covid_19.api.ApiClient;
import com.example.covid_19.api.ApiInterface;
import com.example.covid_19.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends AppCompatActivity {

    public static final String API_KEY = "9858cfa1503d4a079d1f0d22185eb966";
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles =  new ArrayList<>();
    private NewsApdapter adapter;
    private String TAG = News.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news);

        recyclerView = findViewById(R.id.newsRecycler);
        layoutManager = new LinearLayoutManager(News.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        LoadJson();
    }

    public void LoadJson(){

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();
        String q = "covid";

        Call<NewsHelperClass> call;
        call = apiInterface.getNew(country,q,API_KEY);

        call.enqueue(new Callback<NewsHelperClass>() {
            @Override
            public void onResponse(Call<NewsHelperClass> call, Response<NewsHelperClass> response) {
                if(response.isSuccessful() && response.body().getArticle() != null){

                    if (!articles.isEmpty()){
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new NewsApdapter(articles,News.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }else{
                    Toast.makeText(News.this, "No Result",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsHelperClass> call, Throwable t) {

            }
        });
    }
}