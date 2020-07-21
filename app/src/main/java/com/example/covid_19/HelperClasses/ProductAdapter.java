package com.example.covid_19.HelperClasses;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.Activities.Productdetails;
import com.example.covid_19.R;
import com.example.covid_19.utils.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ItemHolder> {

    Context context;
    ArrayList<ProductHelperClass> products;

    public ProductAdapter(Context context, ArrayList<ProductHelperClass> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_design, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        ProductHelperClass productHelperClass = products.get(position);
        holder.txtproductname.setText(productHelperClass.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtproductprice.setText("Price: " + decimalFormat.format(productHelperClass.getProductPrice()) + " VND");
        Picasso.with(context).load(productHelperClass.getProductImage())
                .placeholder(R.drawable.crash_image)
                .error(R.drawable.crash_image)
                .into(holder.productimg);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        ImageView productimg;
        TextView txtproductname,txtproductprice;

        public ItemHolder(View itemView){
            super(itemView);
            productimg = itemView.findViewById(R.id.imageviewproduct);
            txtproductprice = itemView.findViewById(R.id.textviewproductprice);
            txtproductname = itemView.findViewById(R.id.textviewproductname);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Productdetails.class);
                    intent.putExtra("imformationproduct", products.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context, products.get(getAdapterPosition()).getProductName());
                    context.startActivity(intent);
                }
            });
        }
    }

}
