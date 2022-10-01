package com.hacathon.heavyequipmentrent.ui.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CreateOrderBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.OrderCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.List;

import io.realm.Realm;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    Context context;
    List<CreateOrderBean> ordersList;
    OrderCallBacks callBacks;


    public OrdersAdapter(Context context, OrderCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
        ordersList = Realm.getDefaultInstance().where(CreateOrderBean.class).findAll();
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


        CreateOrderBean bean = ordersList.get(position);

        holder.textView_order_number.setText(bean.getId().toString());


        holder.ratingBar_order.setIsIndicator(true);

        if (bean.getState() == Long.valueOf(Constants.NEW)){
            holder.textView_order_status.setText(context.getString(R.string.cell_orders_order_state_new));
        }else if (bean.getState() == Long.valueOf(Constants.COMPLETED)){
            holder.textView_order_status.setText(context.getString(R.string.cell_orders_order_state_completed));
            holder.ratingBar_order.setIsIndicator(false);
        }else if (bean.getState() == Long.valueOf(Constants.EXPIRED)){
            holder.textView_order_status.setText(context.getString(R.string.cell_orders_order_state_expired));
        }else if (bean.getState() == Long.valueOf(Constants.DELETED)){
            holder.textView_order_status.setText(context.getString(R.string.cell_orders_order_state_deleted));
        }

        holder.textView_order_date.setText(bean.getRentStartDate().substring(0, 10));
        holder.textView_order_end_date.setText(bean.getRenToDate().substring(0, 10));
        holder.textView_order_details.setText(bean.getProjectDescription());

        holder.textView_order_category.setText(LanguageManager.isCurrentLangARabic() ? bean.getCategoryTitleAr() : bean.getCategoryTitleEn());
        holder.textView_order_sub_category.setText(LanguageManager.isCurrentLangARabic() ? bean.getSubCategoryTitleAr() : bean.getSubCategoryTitleEn());



        Glide.with(MyApplication.getInstance().getApplicationContext())
                .load(String.valueOf(bean.getImage()))
                .centerCrop()
                .placeholder(R.drawable.order_img_2)
                .error(R.mipmap.ic_launcher)
                .into(holder.imageView_order_image);



        holder.ratingBar_order.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (callBacks != null){
                   holder.textView_submit.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.textView_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.textView_submit.setVisibility(View.GONE);
                callBacks.ratingSelected(bean.getId(), holder.ratingBar_order.getRating());
            }
        });


    }

    @Override
    public int getItemCount() {

        if (ordersList != null && ordersList.size() > 0){
            return ordersList.size();
        }

        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_order_number, textView_order_date, textView_order_status, textView_order_end_date, textView_order_details, textView_order_category, textView_order_sub_category, textView_submit;
        ImageView imageView_order_image;
        RatingBar ratingBar_order;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_order_image = itemView.findViewById(R.id.imageView_order_image);
            textView_order_number = itemView.findViewById(R.id.textView_order_number);
            textView_order_date = itemView.findViewById(R.id.textView_order_date);
            textView_order_status = itemView.findViewById(R.id.textView_order_status);
            textView_order_end_date = itemView.findViewById(R.id.textView_order_end_date);
            textView_order_details = itemView.findViewById(R.id.textView_details);
            textView_order_category = itemView.findViewById(R.id.textView_order_category);
            textView_order_sub_category = itemView.findViewById(R.id.textView_order_sub_category);
            ratingBar_order = itemView.findViewById(R.id.ratingBar_order);
            textView_submit = itemView.findViewById(R.id.textView_submit);
        }
    }

}
