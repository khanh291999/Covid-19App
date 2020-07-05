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
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.covid_19.HelperClasses.ProductAdapter;
import com.example.covid_19.HelperClasses.ProductHelperClass;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class Products extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Variable
    static final float END_SCALE = 0.7f;

    RecyclerView productRecycler;
    RecyclerView.Adapter productAdapter;
    LinearLayout contentView;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_products);

        Hooks();
        productRecycler();
        navigationDrawer();
    }

    private void Hooks() {
        //Recycler
        productRecycler = findViewById(R.id.product_recycler);
        //menu hook
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        menuicon = findViewById(R.id.menu_icon);
        contentView = findViewById(R.id.content);
    }

    private void productRecycler() {
        productRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        ArrayList<ProductHelperClass> products = new ArrayList<>();

        products.add(new ProductHelperClass(R.drawable.face_mask, "Face Mask", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        products.add(new ProductHelperClass(R.drawable.latex_disposable_gloves_100pcs, "Latex Gloves 100pcs", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));
        products.add(new ProductHelperClass(R.drawable.panadol_120v, "Panadol 120pcs", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));

        productAdapter = new ProductAdapter(products);
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
