package com.example.covid_19.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.covid_19.HelperClasses.CartAdapter;
import com.example.covid_19.R;
import com.example.covid_19.utils.CheckConnection;

import java.text.DecimalFormat;

public class ShoppingCart extends AppCompatActivity {

    ListView lvcart;
    TextView txtnotification;
    static TextView txttotal;
    Button btnPay, btnCon;
    ImageView imggoback;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_shopping_cart);

        Hooks();
        ActionGoBack();
        CheckData();
        EventUtil();
        CatchOnItemListView();
        GoEventButton();
    }

    private void GoEventButton() {
        btnCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Products.class));
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Products.arraycart.size() > 0){
                    startActivity(new Intent(getApplicationContext(), UserInformation.class));
                }
                else{
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Your cart is empty");
                }
            }
        });
    }

    //delete product (dont want to buy it anymore)
    private void CatchOnItemListView() {
        lvcart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Context context;
                AlertDialog.Builder builder = new AlertDialog.Builder(ShoppingCart.this);
                builder.setTitle("Confirm delete product");
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Products.arraycart.size() <= 0){
                            txtnotification.setVisibility(View.VISIBLE);
                        }
                        else{
                            Products.arraycart.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            EventUtil();
                            if (Products.arraycart.size() <= 0){
                                txtnotification.setVisibility(View.VISIBLE);
                            }
                            else{
                                txtnotification.setVisibility(View.INVISIBLE);
                            }
                        }
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cartAdapter.notifyDataSetChanged();
                        EventUtil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    //update total cost in cart
    public static void EventUtil() {
        long totalcost = 0;
        for (int i = 0; i < Products.arraycart.size() ; i ++){
            totalcost += Products.arraycart.get(i).getProductPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txttotal.setText(decimalFormat.format(totalcost) + "VND");

    }

    private void CheckData() {
        if(Products.arraycart.size() <= 0 ){
            cartAdapter.notifyDataSetChanged();
            txtnotification.setVisibility(View.VISIBLE);
            lvcart.setVisibility(View.INVISIBLE);
        }else{
            cartAdapter.notifyDataSetChanged();
            txtnotification.setVisibility(View.INVISIBLE);
            lvcart.setVisibility(View.VISIBLE);
        }

    }

    private void ActionGoBack() {
        imggoback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Hooks() {
        imggoback = findViewById(R.id.imgGoBack);
        lvcart = findViewById(R.id.listviewcart);
        txtnotification = findViewById(R.id.textviewnotification);
        txttotal = findViewById(R.id.textviewtotalcost);
        btnPay = findViewById(R.id.btnPay);
        btnCon = findViewById(R.id.btncontinueshopping);
        cartAdapter = new CartAdapter(ShoppingCart.this, Products.arraycart);
        lvcart.setAdapter(cartAdapter);
    }
}