package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.covid_19.HelperClasses.MethodAdapter;
import com.example.covid_19.HelperClasses.MethodHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Methods extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variable
    static final float END_SCALE = 0.7f;

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
        setContentView(R.layout.activity_methods);

        Hooks();
        methodRecycler();

        //Navigation Drawer
        navigationDrawer();
    }

    private void methodRecycler() {
        methodRecycler.setHasFixedSize(true);
        methodRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<MethodHelperClass> methods = new ArrayList<>();

        methods.add(new MethodHelperClass(R.drawable.aed, "Self Check", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        methods.add(new MethodHelperClass(R.drawable.pillbottle, "Doctor advice", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        methods.add(new MethodHelperClass(R.drawable.syring, "Shots", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));

        methodAdapter = new MethodAdapter(methods);
        methodRecycler.setAdapter(methodAdapter);
    }

    private void Hooks() {
        //methods
        methodRecycler = findViewById(R.id.main_methods_recycler);

        //menu hook
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuicon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
    }

    //Navigation Drawer function
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //set of chose menu item
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                goHome();
                break;
            case R.id.nav_track:
                goTrackCountries();
                break;
            case R.id.nav_shopping:
                goShopping();
                break;
            case R.id.nav_method:
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
        navigationView.setCheckedItem(R.id.nav_method);

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
        Intent intent = new Intent(Methods.this, Products.class);
        startActivity(intent);
    }

    private void goTrackCountries() {
        Intent intent = new Intent(Methods.this, AffectedCountries.class);
        startActivity(intent);
    }

    private void goLogin() {
        Intent intent = new Intent(Methods.this, Login.class);
        startActivity(intent);
    }

    private void goProfile() {
        Intent intent = new Intent(Methods.this, UserProfile.class);
        startActivity(intent);
    }

    private void goHome() {
        Intent intent = new Intent(Methods.this, MainActivity.class);
        startActivity(intent);
    }


}
