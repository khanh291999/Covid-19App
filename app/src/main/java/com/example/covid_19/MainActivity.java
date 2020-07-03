package com.example.covid_19;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import androidx.appcompat.view.menu.MenuView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variable
    static final float END_SCALE = 0.7f;

    //Others
    TextView tvCases, tvRecovered, tvTotalDeaths, tvTodayCases , tvTodayDeaths, tvTodayRecovered;
    ScrollView scrollView;
    Button btnTrack;
    MenuView.ItemView productSelected;
    TextView tvDate1,tvDate2;

    RelativeLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Hooks();
        fetchData();
        getDate();

        //Navigation Drawer
        navigationDrawer();

    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            tvCases.setText(jsonObject.getString("cases"));
                            tvRecovered.setText(jsonObject.getString("recovered"));
                            tvTotalDeaths.setText(jsonObject.getString("deaths"));
                            tvTodayCases.setText(jsonObject.getString("todayCases"));
                            tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                            tvTodayRecovered.setText(jsonObject.getString("todayRecovered"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                            scrollView.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

    //set to OnClick tool
    public void goTrackCountries(View view) {
        Intent intent = new Intent(MainActivity.this, AffectedCountries.class);
        startActivity(intent);
    }

    public void goShopping(View view) {
        Intent intent = new Intent(MainActivity.this, Products.class);
        startActivity(intent);
    }

    public void goMethods(View view) {
        Intent intent = new Intent(MainActivity.this, Methods.class);
        startActivity(intent);
    }

    private void Hooks() {
        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvTodayRecovered = findViewById(R.id.tvTodayRecovered);

        scrollView = findViewById(R.id.scrollStats);
        btnTrack = findViewById(R.id.btnTrack);
        tvDate1 = findViewById(R.id.tv_date1);
        tvDate2 = findViewById(R.id.tv_date2);

        //menu hook
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuicon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        productSelected = findViewById((R.id.nav_shopping));
    }

    //Navigation Drawer function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //set of chose menu item
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                break;
            case R.id.nav_track:
                goTrackCountries();
                break;
            case R.id.nav_shopping:
                goShopping();
                break;
            case R.id.nav_method:
                goMethods();
                break;
            case R.id.nav_favourite_method:
                Toast.makeText(this,"Favourite Method",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_login:
                goLogin();
                break;
            case R.id.nav_profile:
                goProfile();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigationDrawer() {

        //Hide or show item
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_logout).setVisible(false);
        menu.findItem(R.id.nav_profile).setVisible(false);

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

    //set of chose menu item
    private void goShopping() {
        Intent intent = new Intent(MainActivity.this, Products.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goTrackCountries() {
        Intent intent = new Intent(MainActivity.this, AffectedCountries.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goLogin() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goProfile() {
        Intent intent = new Intent(MainActivity.this, UserProfile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goMethods() {
        Intent intent = new Intent(MainActivity.this, Methods.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    //get date
    private void getDate(){
        Calendar calendar = Calendar.getInstance();
        String currentData = DateFormat.getDateInstance(DateFormat.DEFAULT).format(calendar.getTime());

        tvDate1.setText("Newest Update " + currentData);
        tvDate2.setText("Newest Update " + currentData);
    }
}
