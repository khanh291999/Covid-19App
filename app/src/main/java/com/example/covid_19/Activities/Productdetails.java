package com.example.covid_19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.covid_19.HelperClasses.CartHelperClass;
import com.example.covid_19.HelperClasses.ProductHelperClass;
import com.example.covid_19.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class Productdetails extends AppCompatActivity {

    ImageView imgDetail, imgBack;
    TextView txtName, txtPrice, txtDesc;
    Spinner spinner;
    Button btnBuy;

    //
    ImageView imgshoppingcart;
    //
    int id = 0;
    String DetailName = "";
    int DetailPrice = 0;
    String DetailImage = "";
    String DetailDesc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_productdetails);

        Hooks();
        GetInformation();
        CatchEventSpinner();
        //cart
        EventButton();
        GoToCart();
    }

    private void GoToCart() {
        imgshoppingcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
            }
        });
    }

    private void EventButton() {
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Products.arraycart.size() > 0) {
                    int t = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits = false;
                    for (int i = 0; i < Products.arraycart.size(); i++) {
                        if (Products.arraycart.get(i).getProductID() == id) {
                            Products.arraycart.get(i).setNumberOfProduct(Products.arraycart.get(i).getNumberOfProduct() + t);
                            if (Products.arraycart.get(i).getNumberOfProduct() >= 10) {
                                Products.arraycart.get(i).setNumberOfProduct(10);
                            }
                            Products.arraycart.get(i).setProductPrice(DetailPrice * Products.arraycart.get(i).getNumberOfProduct());
                            exits = true;
                        }
                    }
                    if (exits == false) {
                        int count = Integer.parseInt(spinner.getSelectedItem().toString());
                        long newPrice = count * DetailPrice;
                        Products.arraycart.add(new CartHelperClass(id, DetailName, DetailPrice, DetailImage, count));
                    }
                } else {
                    int count = Integer.parseInt(spinner.getSelectedItem().toString());
                    long newPrice = count * DetailPrice;
                    Products.arraycart.add(new CartHelperClass(id, DetailName, DetailPrice, DetailImage, count));
                }

                //jump to cart
                startActivity(new Intent(getApplicationContext(), ShoppingCart.class));
            }
        });
    }

    //product click

    private void CatchEventSpinner() {
        Integer[] number = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, number);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        ProductHelperClass productHelperClass = (ProductHelperClass) getIntent().getSerializableExtra("imformationproduct");
        id = productHelperClass.getID();
        DetailName = productHelperClass.getProductName();
        DetailPrice = productHelperClass.getProductPrice();
        DetailImage = productHelperClass.getProductImage();
        DetailDesc = productHelperClass.getProductDescription();
        txtName.setText(DetailName);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtPrice.setText("Price: " + decimalFormat.format(DetailPrice) + " VND");
        txtDesc.setText(DetailDesc);
        Picasso.with(getApplicationContext()).load(DetailImage)
                .placeholder(R.drawable.crash_image)
                .error(R.drawable.crash_image)
                .into(imgDetail);
    }

    private void Hooks() {
        imgshoppingcart = findViewById(R.id.imageviewgotocart);
        imgDetail = findViewById(R.id.imageviewproductdetail);
        txtName = findViewById(R.id.textviewproductnamedetail);
        txtPrice = findViewById(R.id.textviewproductpricedetail);
        txtDesc = findViewById(R.id.textviewproductdescriptiondetail);
        spinner = findViewById(R.id.spinner);
        btnBuy = findViewById(R.id.buttonbuyproduct);
        imgBack = findViewById(R.id.imgBack);
    }

    public void gotoPreviousActivity(View v) {
        finish();
    }
}
