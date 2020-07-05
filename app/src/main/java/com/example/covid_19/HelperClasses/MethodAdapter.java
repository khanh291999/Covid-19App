package com.example.covid_19.HelperClasses;

import android.graphics.Color;
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
        return methodViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MethodViewHolder holder, int position) {

        MethodHelperClass methodHelperClass = methods.get(position);

        holder.title.setText(methodHelperClass.getTitle());
        holder.image.setImageResource(methodHelperClass.getImage());
        holder.desc.setText(methodHelperClass.getDescription());
    }

    @Override
    public int getItemCount() {
        return methods.size();
    }

    public static class MethodViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title, desc;

        public MethodViewHolder(@NonNull View itemView) {
            super(itemView);

            //Hooks
            title = itemView.findViewById(R.id.methodTitle);
            image = itemView.findViewById(R.id.methodImg);
            desc = itemView.findViewById(R.id.methodDesc);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (image.getDrawable() == image.getResources().getDrawable(R.drawable.plus_icon)) {
//                        desc.setVisibility(View.VISIBLE);
//                        title.setTextColor(Color.parseColor("#E74C3C"));
//                        image.setImageDrawable(image.getResources().getDrawable(R.drawable.minus_icon));
//                    } else if (image.getDrawable() == image.getResources().getDrawable(R.drawable.minus_icon)) {
//                        desc.setVisibility(View.GONE);
//                        title.setTextColor(Color.parseColor("#000000"));
//                        image.setImageDrawable(image.getResources().getDrawable(R.drawable.plus_icon));
//                    }

                    ImageView imageView = (ImageView) image;
                    assert (R.id.methodImg == imageView.getId());

                    Integer integer = (Integer) imageView.getTag();
                    integer = integer == null ? 0 : integer;

                    switch (integer){
                        case R.drawable.plus_icon:
                            desc.setVisibility(View.VISIBLE);
                            title.setTextColor(Color.parseColor("#E74C3C"));
                            image.setImageDrawable(image.getResources().getDrawable(R.drawable.minus_icon));
                            break;
                        case R.drawable.minus_icon:
                            desc.setVisibility(View.GONE);
                            title.setTextColor(Color.parseColor("#000000"));
                            image.setImageDrawable(image.getResources().getDrawable(R.drawable.plus_icon));
                            break;
                    }
                }
            });
        }
    }
}

