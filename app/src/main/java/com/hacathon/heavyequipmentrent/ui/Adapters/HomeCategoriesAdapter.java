package com.hacathon.heavyequipmentrent.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.HomeCallBacks;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>{

    Context context;
    HomeCallBacks callBacks;

    public HomeCategoriesAdapter(Context context, HomeCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_categories_item, null);

        return new HomeCategoriesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.categoryItemClicked(holder.textView_category_name.getText().toString());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_category_name;
        ImageView imageView_category_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_category_image = itemView.findViewById(R.id.imageView_category_image);
            textView_category_name = itemView.findViewById(R.id.textView_category_name);
        }
    }


}
