package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class PreventionOne extends AppCompatActivity {

    RecyclerView favouriteMethodRecycler;
    RecyclerView.Adapter favouriteMethodAdapter;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prevention_one);

    }

    public void goBackMethod1(View view) {
        startActivity(new Intent(PreventionOne.this, Methods.class));
        finish();
    }
}
