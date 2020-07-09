package com.example.covid_19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.covid_19.Model.MethodDetail;
import com.google.android.material.navigation.NavigationView;

public class Methods extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variable
    static final float END_SCALE = 0.7f;

    RelativeLayout contentView;

    LinearLayout linearLayout;
    CardView card1,card2;

    //Drawer Menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ImageView menuicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_methods);

        Hooks();

        //Navigation Drawer
        navigationDrawer();
    }

    private void Hooks() {
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
            case R.id.nav_new:
                Toast.makeText(this,"New",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                startActivity(new Intent(getApplicationContext(), About.class));
                break;
            case R.id.nav_contact:
                startActivity(new Intent(getApplicationContext(), Contact.class));
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigationDrawer() {
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
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goTrackCountries() {
        Intent intent = new Intent(Methods.this, AffectedCountries.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goLogin() {
        Intent intent = new Intent(Methods.this, Login.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goProfile() {
        Intent intent = new Intent(Methods.this, UserProfile.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    private void goHome() {
        Intent intent = new Intent(Methods.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_in_right);
    }

    //Prevention
    public void goPreventionOne(View view){
        startActivity(new Intent(Methods.this, PreventionOne.class));
    }

    public void goPreventionTwo(View view){
        startActivity(new Intent(Methods.this, PreventionTwo.class));
    }

    //Symptoms
    public void goDetailMethod(View view){
        startActivity(new Intent(Methods.this, MethodDetail.class));
    }

}
