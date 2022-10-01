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
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.SubCatCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.List;

import io.realm.Realm;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder>{


    Context context;
    SubCatCallBacks callBacks;
    Long selectedCatId;
    List<SubCategoriesBean> subCategoriesList;

    public SubCategoryAdapter(Context context,  Long selectedCatId, SubCatCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
        this.selectedCatId = selectedCatId;

        subCategoriesList = Realm.getDefaultInstance().where(SubCategoriesBean.class).equalTo("mainCarId", selectedCatId).findAll();
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

        SubCategoriesBean bean = subCategoriesList.get(position);

        holder.textView_sub_category_name.setText(LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn());
        holder.textView_desc_sub.setText(LanguageManager.isCurrentLangARabic() ? bean.getDescriptionAr() : bean.getDescriptionEn());


        Glide.with(MyApplication.getInstance().getApplicationContext())
                .load(bean.getRefrenceImageRaw())
                .centerCrop()
                .placeholder(R.drawable.order_img_2)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView_sub_category_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.subCategoryItemClicked(bean.getSubCatId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {

        if (subCategoriesList != null && subCategoriesList.size() > 0){
            return subCategoriesList.size();
        }

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_sub_category_name, textView_desc_sub;
        ImageView imageView_sub_category_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_sub_category_image = itemView.findViewById(R.id.imageView_sub_category_image);
            textView_sub_category_name = itemView.findViewById(R.id.textView_sub_category_name);
            textView_desc_sub = itemView.findViewById(R.id.textView_desc_sub);
        }
    }


}
