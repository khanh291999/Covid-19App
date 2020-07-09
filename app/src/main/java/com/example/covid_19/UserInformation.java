package com.example.covid_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.covid_19.utils.CheckConnection;
import com.example.covid_19.utils.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserInformation extends AppCompatActivity {

    EditText edtcustomername, edtphonenumber, edtaddress;
    Button btnconfirm;
    ImageView btngoback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_information);

        Hooks();
        btngoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        }
        else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Please check your connection");
        }
    }

    private void EventButton() {
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = edtcustomername.getText().toString().trim();
                final String phonenumber = edtphonenumber.getText().toString().trim();
                final String address = edtaddress.getText().toString().trim();
                if(name.length() > 0 && phonenumber.length()>0 && address.length()>0){
                    Context context;
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.OrderLink, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String orderID) {
                            Log.d("idorder", orderID);
                            if (Integer.parseInt(orderID)>0){
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.DetailOrderLink, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            Products.arraycart.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Purchased Successfully!!");
                                            startActivity(new Intent(getApplicationContext(), Products.class));
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Continue shopping");
                                        }
                                        else{
                                            CheckConnection.ShowToast_Short(getApplicationContext(), "Purchased Unsuccessfully!!");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i =0; i < Products.arraycart.size(); i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("orderID",orderID);
                                                jsonObject.put("productID",Products.arraycart.get(i).getProductID());
                                                jsonObject.put("productName",Products.arraycart.get(i).getProductName());
                                                jsonObject.put("numberOfProduct",Products.arraycart.get(i).getNumberOfProduct());
                                                jsonObject.put("cost",Products.arraycart.get(i).getProductPrice());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String,String>();
                                        hashMap.put("json", jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("customerName", name);
                            hashMap.put("customerNumber", phonenumber);
                            hashMap.put("customerAddress", address);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else{
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Please check inputs value");
                }
            }
        });
    }

    private void Hooks() {
        edtcustomername = findViewById(R.id.edittextcustomername);
        edtphonenumber = findViewById(R.id.edittextcustomernumber);
        edtaddress = findViewById(R.id.edittextcustomeraddress);
        btnconfirm = findViewById(R.id.buttonconfirm);
        btngoback = findViewById(R.id.buttongoback);
    }
}