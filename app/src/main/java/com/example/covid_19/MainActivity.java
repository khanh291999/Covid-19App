package com.example.covid_19;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.HelperClasses.MethodAdapter;
import com.example.covid_19.HelperClasses.MethodHelperClass;
import com.google.android.material.navigation.NavigationView;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variable
    static final float END_SCALE = 0.7f;

    //Attribute
    TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    SimpleArcLoader simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    Button btnTrack;

    RecyclerView methodRecycler;
    RecyclerView.Adapter methodAdapter;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Hooks();
        methodRecycler();
        fetchData();

        //Navigation Drawer
        navigationDrawer();

    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        simpleArcLoader.start();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvCritical.setText(jsonObject.getString("critical"));
                            tvActive.setText(jsonObject.getString("active"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                            pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recoverd", Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Deaths", Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                            pieChart.addPieSlice(new PieModel("Active", Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                            pieChart.startAnimation();

                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);


                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    public void goTrackCountries(View view) {

        //startActivity(new Intent(getApplicationContext(),AffectedCountries.class));
        Intent intent = new Intent(MainActivity.this, AffectedCountries.class);
        startActivity(intent);
    }

    private void methodRecycler() {
        methodRecycler.setHasFixedSize(true);
        methodRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<MethodHelperClass> methods = new ArrayList<>();

        methods.add(new MethodHelperClass(R.drawable.aed, "Self Check", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        methods.add(new MethodHelperClass(R.drawable.pillbottle, "Doctor advice", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        methods.add(new MethodHelperClass(R.drawable.syring, "Shots", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));


        methodAdapter = new MethodAdapter(methods);
        methodRecycler.setAdapter(methodAdapter);
    }

    private void Hooks() {
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);
        btnTrack = findViewById(R.id.btnTrack);

        //methods
        methodRecycler = findViewById(R.id.method_recycler);

        //menu hook
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuicon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
    }

    //Navigation Drawer function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return true;
    }

    private void navigationDrawer() {
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        //set menu icon
        menuicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer((GravityCompat.START));
                else drawerLayout.openDrawer((GravityCompat.START));

            }
        });

        animatedNavigationDrawer();
    }

    private void animatedNavigationDrawer() {

        drawerLayout.setScrimColor(getResources().getColor(R.color.colorAccent));

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }
}
