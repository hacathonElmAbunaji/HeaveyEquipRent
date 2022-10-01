package com.hacathon.heavyequipmentrent.ui.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CategoriesBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.HomeCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.List;

import io.realm.Realm;

public class HomeCategoriesAdapter extends RecyclerView.Adapter<HomeCategoriesAdapter.ViewHolder>{

    Context context;
    HomeCallBacks callBacks;

    List<CategoriesBean> categoriesList;

    public HomeCategoriesAdapter(Context context, HomeCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;

        categoriesList = Realm.getDefaultInstance().where(CategoriesBean.class).findAll();
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

        CategoriesBean bean = categoriesList.get(position);

        holder.textView_category_name.setText(LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn());
        holder.textView_desc.setText(LanguageManager.isCurrentLangARabic() ? bean.getDescriptionAr() : bean.getDescriptionEn());


        Glide.with(MyApplication.getInstance().getApplicationContext())
                .load(bean.getRefrenceImageRaw())
                .centerCrop()
                .placeholder(R.drawable.order_img_2)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView_category_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.categoryItemClicked(bean.getCatId());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categoriesList != null && categoriesList.size() > 0){
          return categoriesList.size();
        }

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_category_name, textView_desc;
        ImageView imageView_category_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_category_image = itemView.findViewById(R.id.imageView_category_image);
            textView_category_name = itemView.findViewById(R.id.textView_category_name);
            textView_desc = itemView.findViewById(R.id.textView_desc);
        }
    }


}
