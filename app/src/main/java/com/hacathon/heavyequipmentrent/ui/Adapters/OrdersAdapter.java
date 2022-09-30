package com.hacathon.heavyequipmentrent.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.R;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    Context context;

    public OrdersAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_orders_item, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (position == 0){
            holder.imageView_order_image.setImageResource(R.drawable.order_img_2);
        }

        if (position == 1){
            holder.imageView_order_image.setImageResource(R.drawable.order_img);
        }

        if (position == 2){
            holder.imageView_order_image.setImageResource(R.drawable.order_img_2);
        }

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_order_number, textView_order_date, textView_order_status, textView_order_price, textView_order_details;
        ImageView imageView_order_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_order_image = itemView.findViewById(R.id.imageView_order_image);
            textView_order_number = itemView.findViewById(R.id.textView_order_number);
            textView_order_date = itemView.findViewById(R.id.textView_order_date);
            textView_order_status = itemView.findViewById(R.id.textView_order_status);
            textView_order_price = itemView.findViewById(R.id.textView_order_price);
            textView_order_details = itemView.findViewById(R.id.textView_details);
        }
    }

}
