package com.example.covid_19.HelperClasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covid_19.R;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    ArrayList<ProductHelperClass> products;

    public ProductAdapter(ArrayList<ProductHelperClass> Products) {
        this.products = Products;
    }

    @NonNull
    @Override
    public ProductAdapter.ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_card_design, parent, false);
        ProductAdapter.ProductViewHolder productViewHolder = new ProductViewHolder((view));
        return  productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductHelperClass productHelperClass = products.get(position);

        holder.image.setImageResource(productHelperClass.getImage());
        holder.title.setText(productHelperClass.getTitle());
        holder.desc.setText(productHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.product_image);
            title = itemView.findViewById(R.id.product_title);
            desc = itemView.findViewById(R.id.product_desc);

        }
    }
}
