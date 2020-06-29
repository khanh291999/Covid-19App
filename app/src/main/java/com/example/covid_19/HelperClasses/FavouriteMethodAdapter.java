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

public class FavouriteMethodAdapter extends RecyclerView.Adapter<FavouriteMethodAdapter.FavouriteMethodViewHolder>{
    ArrayList<FavouriteMethodHelperClass> methods;

    public FavouriteMethodAdapter(ArrayList<FavouriteMethodHelperClass> methods) {
        this.methods = methods;
    }

    @NonNull
    @Override
    public FavouriteMethodAdapter.FavouriteMethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_card_design, parent, false);
        FavouriteMethodAdapter.FavouriteMethodViewHolder favouriteMethodViewHolder = new FavouriteMethodAdapter.FavouriteMethodViewHolder((view));
        return  favouriteMethodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMethodAdapter.FavouriteMethodViewHolder holder, int position) {

        FavouriteMethodHelperClass methodHelperClass = methods.get(position);

        holder.image.setImageResource(methodHelperClass.getImage());
        holder.title.setText(methodHelperClass.getTitle());
        holder.desc.setText(methodHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    public static class FavouriteMethodViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public FavouriteMethodViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.method_image);
            title = itemView.findViewById(R.id.method_title);
            desc = itemView.findViewById(R.id.method_desc);

        }
    }
}
