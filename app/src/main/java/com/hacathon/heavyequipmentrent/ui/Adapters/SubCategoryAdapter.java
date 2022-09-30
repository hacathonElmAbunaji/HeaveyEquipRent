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
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.SubCatCallBacks;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>{


    Context context;
    SubCatCallBacks callBacks;

    public SubCategoryAdapter(Context context, SubCatCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_sub_category_item, null);

        return new SubCategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.subCategoryItemClicked(holder.textView_sub_category_name.getText().toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_sub_category_name;
        ImageView imageView_sub_category_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_sub_category_image = itemView.findViewById(R.id.imageView_sub_category_image);
            textView_sub_category_name = itemView.findViewById(R.id.textView_sub_category_name);
        }
    }


}
