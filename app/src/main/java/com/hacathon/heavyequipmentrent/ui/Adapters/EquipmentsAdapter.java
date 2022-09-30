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
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.EquipmentCallBacks;

public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.ViewHolder> {

    Context context;
    EquipmentCallBacks callBacks;

    public EquipmentsAdapter(Context context, EquipmentCallBacks callBacks) {
        this.context = context;
        this.callBacks = callBacks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cell_equipments, null);

        return new EquipmentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (callBacks != null){
                    callBacks.equipmentSelected(holder.textView_equipment_name.getText().toString());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_equipment_name;
        ImageView imageView_equipment_image;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView_equipment_image = itemView.findViewById(R.id.imageView_equipment_image);
            textView_equipment_name = itemView.findViewById(R.id.textView_equipment_name);
        }
    }

}
