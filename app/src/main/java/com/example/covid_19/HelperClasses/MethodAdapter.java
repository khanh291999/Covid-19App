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

public class MethodAdapter extends RecyclerView.Adapter<MethodAdapter.MethodViewHolder> {

    ArrayList<MethodHelperClass> methods;

    public MethodAdapter(ArrayList<MethodHelperClass> methods) {
        this.methods = methods;
    }

    @NonNull
    @Override
    public MethodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.method_card_design, parent, false);
        MethodViewHolder methodViewHolder = new MethodViewHolder((view));
        return  methodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MethodViewHolder holder, int position) {

        MethodHelperClass methodHelperClass = methods.get(position);

        holder.image.setImageResource(methodHelperClass.getImage());
        holder.title.setText(methodHelperClass.getTitle());
        holder.desc.setText(methodHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    public static class MethodViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc;

        public MethodViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.method_image);
            title = itemView.findViewById(R.id.method_title);
            desc = itemView.findViewById(R.id.method_desc);

        }
    }
}
