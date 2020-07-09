package com.example.covid_19.HelperClasses;

import android.app.Notification;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.example.covid_19.MainActivity;
import com.example.covid_19.Products;
import com.example.covid_19.R;
import com.example.covid_19.ShoppingCart;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {
    Context context;
    ArrayList<CartHelperClass> arraycart;

    public CartAdapter(Context context, ArrayList<CartHelperClass> arraycart) {
        this.context = context;
        this.arraycart = arraycart;
    }

    @Override
    public int getCount() {
        return arraycart.size();
    }

    @Override
    public Object getItem(int position) {
        return arraycart.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        TextView txtcartname, txtcartprice;
        ImageView imgcart;
        Button btnminus, btnvalue, btnplus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cart_line, parent, false);
            viewHolder.txtcartname = convertView.findViewById(R.id.textviewcartname);
            viewHolder.txtcartprice = convertView.findViewById(R.id.textviewcartprice);
            viewHolder.imgcart = convertView.findViewById(R.id.imageviewcart);
            viewHolder.btnminus = convertView.findViewById(R.id.buttonminus);
            viewHolder.btnvalue = convertView.findViewById(R.id.buttonvalue);
            viewHolder.btnplus = convertView.findViewById(R.id.buttonplus);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CartHelperClass cartHelperClass = (CartHelperClass) getItem(position);
        viewHolder.txtcartname.setText(cartHelperClass.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtcartprice.setText(decimalFormat.format(cartHelperClass.getProductPrice())+ " VND");
        Picasso.with(context).load(cartHelperClass.getProductImage())
                .placeholder(R.drawable.crash_image)
                .error(R.drawable.crash_image)
                .into(viewHolder.imgcart);
        viewHolder.btnvalue.setText(String.format("%d", cartHelperClass.getNumberOfProduct()));
        int count = Integer.parseInt(viewHolder.btnvalue.getText().toString());
        if(count >= 10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        else if (count <= 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        }
        else if (count > 1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newcount = Integer.parseInt(finalViewHolder.btnvalue.getText().toString()) + 1;
                int recentcount = Products.arraycart.get(position).getNumberOfProduct();
                long recentprice = Products.arraycart.get(position).getProductPrice();

                Products.arraycart.get(position).setNumberOfProduct(newcount);
                long newprice = (recentprice * newcount) / recentcount;
                Products.arraycart.get(position).setProductPrice(newprice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartprice.setText(decimalFormat.format(newprice)+ " VND");
                ShoppingCart.EventUtil();
                if (newcount > 9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(newcount));
                }
                else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(newcount));
                }
            }
        });

        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int newcount = Integer.parseInt(finalViewHolder.btnvalue.getText().toString()) - 1;
                int recentcount = Products.arraycart.get(position).getNumberOfProduct();
                long recentprice = Products.arraycart.get(position).getProductPrice();

                Products.arraycart.get(position).setNumberOfProduct(newcount);
                long newprice = (recentprice * newcount) / recentcount;
                Products.arraycart.get(position).setProductPrice(newprice);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtcartprice.setText(decimalFormat.format(newprice)+ " VND");
                ShoppingCart.EventUtil();
                if (newcount < 9){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(newcount));
                }
                else{
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalue.setText(String.valueOf(newcount));
                }
            }
        });
        return convertView;
    }
}
