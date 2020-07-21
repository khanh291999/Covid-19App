package com.example.covid_19.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid_19.HelperClasses.Article;
import com.example.covid_19.HelperClasses.NewsApdapter;
import com.example.covid_19.HelperClasses.NewsHelperClass;
import com.example.covid_19.R;
import com.example.covid_19.api.ApiClient;
import com.example.covid_19.api.ApiInterface;
import com.example.covid_19.utils.Utils;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class News extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public static final String API_KEY = "9858cfa1503d4a079d1f0d22185eb966";
    private RecyclerView recyclerView;
    private MaterialSearchBar search;
    private TextView topHeadLines;
    private RecyclerView.LayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Article> articles = new ArrayList<>();
    private NewsApdapter adapter;
    private String TAG = News.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_news);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        topHeadLines = findViewById(R.id.topheadlines);
        recyclerView = findViewById(R.id.newsRecycler);
        layoutManager = new LinearLayoutManager(News.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        search = findViewById(R.id.new_search_bar);
        search.setHint("Enter your keyword");

        search.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                if (!enabled) {
                    onLoadingSwipeRefresh("covid");
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                final StringBuilder sb = new StringBuilder(text.length());
                sb.append(text);
                onLoadingSwipeRefresh(sb.toString());
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            }
        });

        onLoadingSwipeRefresh("covid");
    }

    public void LoadJson(final String keyword) {

        topHeadLines.setVisibility(View.INVISIBLE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        String country = Utils.getCountry();
        String q = "covid";
        String language = Utils.getLanguage();

        Call<NewsHelperClass> call;

        if (keyword.length() > 0) {
            call = apiInterface.getNewsSearch(keyword, "en", "publishedAt", API_KEY);
        } else {
            call = apiInterface.getNew(country, q, API_KEY);
        }

        call.enqueue(new Callback<NewsHelperClass>() {
            @Override
            public void onResponse(Call<NewsHelperClass> call, Response<NewsHelperClass> response) {
                if (response.isSuccessful() && response.body().getArticle() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new NewsApdapter(articles, News.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                    topHeadLines.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);


                } else {
                    topHeadLines.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(News.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewsHelperClass> call, Throwable t) {
                topHeadLines.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onRefresh() {
        LoadJson("covid");
    }

    private void onLoadingSwipeRefresh(final String keyword) {
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        LoadJson(keyword);
                    }
                }
        );
    }


    private void initListener(){

        adapter.setOnItemClickListener(new NewsApdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), NewsDetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("newimg", article.getUrlToImage());
                intent.putExtra("date", article.getPublishedAt());
                intent.putExtra("source", article.getSource().getName());
                intent.putExtra("author", article.getAuthor());

                startActivity(intent);
            }

        });
    }


}