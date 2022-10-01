package com.hacathon.heavyequipmentrent.ui.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.EquipmentsBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.EquipmentCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.List;

import io.realm.Realm;

public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.ViewHolder> {

    Context context;
    EquipmentCallBacks callBacks;
    List<EquipmentsBean> list;
    Long selectedCatId;
    Long selectedSubCatId;

    public EquipmentsAdapter(Context context,   Long selectedCatId, Long selectedSubCatId, EquipmentCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
        this.selectedCatId = selectedCatId;
        this.selectedSubCatId = selectedSubCatId;


        list = Realm.getDefaultInstance().where(EquipmentsBean.class).equalTo("mainCatId", selectedCatId).equalTo("subCatId", selectedSubCatId).findAll();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_equipments, null);

        return new EquipmentsAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        EquipmentsBean bean = list.get(position);

        holder.textView_equip_desc.setText(LanguageManager.isCurrentLangARabic() ? bean.getDescriptionAr() : bean.getDescriptionEn());
        holder.textView_equip_title.setText(LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn());
        holder.textView_supplier_name_value.setText(LanguageManager.isCurrentLangARabic() ? bean.getSupplierNameAr() : bean.getSupplierNameEn());
        holder.textView_price_value.setText(
                context.getString(R.string.cell_equipment_daily) + " " +  bean.getDailyPrice() + " " + context.getString(R.string.cell_equipment_SR) + " , " +
                        context.getString(R.string.cell_equipment_weekly) + " " +  bean.getWeeklyPrice() + " " + context.getString(R.string.cell_equipment_SR) + " , " +
                        context.getString(R.string.cell_equipment_monthly) + " " +  bean.getMonthlyPrice() + " " + context.getString(R.string.cell_equipment_SR)
        );

        holder.textView_height.setText(bean.getHeight().toString() + context.getString(R.string.cell_equipment_height_count));
        holder.textView_weight.setText(bean.getWieght().toString() + context.getString(R.string.cell_equipment_weight_count));
        holder.textView_quantity.setText(bean.getQuantity().toString());


        Glide.with(MyApplication.getInstance().getApplicationContext())
                .load(bean.getEquipmentImages().get(0).getImageId())
                .centerCrop()
                .placeholder(R.drawable.order_img_2)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView_equipment_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.equipmentSelected(bean.getId());
                }
            }
        });


        holder.img_btn_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBacks.supplierClicked(bean.getSupplierId());
            }
        });

    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0){
            return list.size();
        }

        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_equipment_name, textView_equip_desc, textView_equip_title, textView_supplier_name_value, textView_price_value, textView_height, textView_weight, textView_quantity;
        ImageView imageView_equipment_image;
        ImageButton img_btn_supplier;
        RatingBar ratingBar;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_equipment_image = itemView.findViewById(R.id.imageView_equipment_image);
            textView_equipment_name = itemView.findViewById(R.id.textView_equipment_name);
            textView_equip_desc = itemView.findViewById(R.id.textView_equip_desc);
            textView_equip_title = itemView.findViewById(R.id.textView_equip_title);
            img_btn_supplier = itemView.findViewById(R.id.img_btn_supplier);
            textView_supplier_name_value = itemView.findViewById(R.id.textView_supplier_name_value);
            textView_price_value = itemView.findViewById(R.id.textView_price_value);

            textView_height = itemView.findViewById(R.id.textView_height);
            textView_weight = itemView.findViewById(R.id.textView_weight);
            textView_quantity = itemView.findViewById(R.id.textView_quantity);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

}
