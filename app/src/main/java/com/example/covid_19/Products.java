package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.HelperClasses.ProductAdapter;
import com.example.covid_19.HelperClasses.ProductHelperClass;
import com.example.covid_19.utils.CheckConnection;
import com.example.covid_19.utils.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Products extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variable
    static final float END_SCALE = 0.7f;

    RecyclerView productRecycler;
    ViewFlipper viewFlipperAdverties;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon;

    //new
    ArrayList<ProductHelperClass> productarray;
    ProductAdapter productAdapter;
    private static final String[] LOCATION_PERMS={
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_products);

        Hooks();
        navigationDrawer();
        
        //new
        ActionViewFlipper();
        GetProductData();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionViewFlipper();
            GetProductData();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(LOCATION_PERMS, 1337);
            }
        }else{
            CheckConnection.ShowToast_Short(getApplicationContext(), "You need to check the connection");
            finish();
        }
    }

    private void GetProductData() {
        Context context;
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.ProductLink, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    int ID = 0;
                    String Productname = "";
                    Integer Productprice = 0;
                    String Productimage = "";
                    String Prodcutdescription = "";
                    for(int i = 0; i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Productname = jsonObject.getString("productName");
                            Productprice = jsonObject.getInt("productPrice");
                            Productimage = jsonObject.getString("productImage");
                            Prodcutdescription = jsonObject.getString("productDescription");
                            productarray.add(new ProductHelperClass(ID, Productprice, Productname, Productimage,Prodcutdescription));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://image.freepik.com/free-vector/work-home-during-covid-19-quarantine-virus-prevent-viral-infection-young-pretty-woman-working-with-laptop-computer-home-office-social-distance-concept-flat-illustration_88272-1393.jpg");
        mangquangcao.add("https://cdn.vox-cdn.com/thumbor/X7PBRT6fy0nfDRULNOhuax-viY8=/1400x1050/filters:format(jpeg)/cdn.vox-cdn.com/uploads/chorus_asset/file/19773513/GettyImages_1201647537.jpg");
        mangquangcao.add("https://images.theconversation.com/files/324486/original/file-20200401-66109-l9sy5w.jpg?ixlib=rb-1.1.0&rect=75%2C192%2C4509%2C2385&q=45&auto=format&w=926&fit=clip");
        mangquangcao.add("https://image.freepik.com/free-vector/working-from-home-covid-19-coronavirus_41910-440.jpg");
        for (int i = 0; i< mangquangcao.size(); i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipperAdverties.addView(imageView);
        }
        viewFlipperAdverties.setFlipInterval(5000);
        viewFlipperAdverties.setAutoStart(true);
        Context context;
        Animation animation_in_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.in_right);
        Animation animation_out_right = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.out_right);
        viewFlipperAdverties.setInAnimation(animation_in_right);
        viewFlipperAdverties.setOutAnimation(animation_out_right);
    }

    private void Hooks() {
        //ViewFlipper
        viewFlipperAdverties = findViewById(R.id.viewFlipperAdverties);
        //Recycler
        productRecycler = findViewById(R.id.productRecycler);
        //menu hook
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuicon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
        //new
        productarray = new ArrayList<>();
        productAdapter = new ProductAdapter(getApplicationContext(),productarray);
        productRecycler.setHasFixedSize(true);
        productRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        productRecycler.setAdapter(productAdapter);
    }

    //Navigation Drawer function

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //set of chose menu item
        switch (menuItem.getItemId()){
            case R.id.nav_shopping:
                break;
            case R.id.nav_home:
                goHome();
                break;
            case R.id.nav_track:
                goTrackCountries();
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
        navigationView.setCheckedItem(R.id.nav_shopping);

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

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    //set of chose menu item
    private void goTrackCountries() {
        Intent intent = new Intent(Products.this, AffectedCountries.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(Products.this, MainActivity.class);
        startActivity(intent);
    }

    private void goLogin() {
        Intent intent = new Intent(Products.this, Login.class);
        startActivity(intent);
    }

    private void goProfile() {
        Intent intent = new Intent(Products.this, UserProfile.class);
        startActivity(intent);
    }

    private void goMethods() {
        Intent intent = new Intent(Products.this, Methods.class);
        startActivity(intent);
    }

}
