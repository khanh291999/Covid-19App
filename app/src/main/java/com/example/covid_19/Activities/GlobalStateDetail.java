package com.example.covid_19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.R;

import org.json.JSONException;
import org.json.JSONObject;

public class GlobalStateDetail extends AppCompatActivity {

    TextView tvDetailDeaths, tvDetailInfected, tvDetailRecovered, tvPopulation, tvAffectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_global_state_detail);

        Hooks();
        fetchData();
    }

    public void goHome(View view) {
        Intent intent = new Intent(GlobalStateDetail.this, MainActivity.class);
        startActivity(intent);
    }

    private void Hooks() {
        tvDetailDeaths = findViewById(R.id.tvDetailDeaths);
        tvDetailInfected = findViewById(R.id.tvDetailInfected);
        tvDetailRecovered = findViewById(R.id.tvDetailRecovered);
        tvPopulation = findViewById(R.id.tvDetailPopulation);
        tvAffectedCountry = findViewById(R.id.tvDetailAffectedCountry);
    }

    private void fetchData() {

        String url = "https://corona.lmao.ninja/v2/all/";

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());

                            tvDetailDeaths.setText(jsonObject.getString("deaths"));
                            tvDetailInfected.setText(jsonObject.getString("cases"));
                            tvDetailRecovered.setText(jsonObject.getString("recovered"));
                            tvPopulation.setText(jsonObject.getString("population"));
                            tvAffectedCountry.setText(jsonObject.getString("affectedCountries"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GlobalStateDetail.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);


    }

}
