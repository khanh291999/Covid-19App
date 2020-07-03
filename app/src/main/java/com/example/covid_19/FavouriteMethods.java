package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.covid_19.HelperClasses.FavouriteMethodAdapter;
import com.example.covid_19.HelperClasses.FavouriteMethodHelperClass;

import java.util.ArrayList;

public class FavouriteMethods extends AppCompatActivity {

    RecyclerView favouriteMethodRecycler;
    RecyclerView.Adapter favouriteMethodAdapter;
    LinearLayout contentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_favourite_methods);

        Hooks();
        favouriteMethodRecycler();
    }

    private void favouriteMethodRecycler() {
        favouriteMethodRecycler.setHasFixedSize(true);
        favouriteMethodRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        ArrayList<FavouriteMethodHelperClass> methods = new ArrayList<>();


        methods.add(new FavouriteMethodHelperClass(R.drawable.pillbottle,R.drawable.ic_fill_heart, "Doctor advice", "aaaaa aaaaaaaa aaaaaa aaaaaaa aaaaaa aaaaaaa"));


        favouriteMethodAdapter = new FavouriteMethodAdapter(methods);
        favouriteMethodRecycler.setAdapter(favouriteMethodAdapter);
    }


    private void Hooks() {
        //methods
        favouriteMethodRecycler = findViewById(R.id.favourite_recycler);
    }
}
