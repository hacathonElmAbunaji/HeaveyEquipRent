package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.EquipmentsBean;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SupplierInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SupplierInfoFragment extends BottomSheetDialogFragment {


    View view;
    Long equipId;

    ImageView imageView_supplier_image;

    public SupplierInfoFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SupplierInfoFragment newInstance(String param1, String param2) {
        SupplierInfoFragment fragment = new SupplierInfoFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }//OnCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_supplier_info, container, false);

        setOnClickListeners();
        iniLayouts();

        return view;
    }//OnCreateView


    private void iniLayouts(){
        imageView_supplier_image = view.findViewById(R.id.imageView_supplier_image);

        EquipmentsBean bean = Realm.getDefaultInstance().where(EquipmentsBean.class).equalTo("id", equipId).findFirst();

        if (bean != null && bean.isValid() && bean.getEquipmentImages() != null){
            Glide.with(MyApplication.getInstance().getApplicationContext())
                    .load(bean.getEquipmentImages().get(0).getImageId())
                    .centerCrop()
                    .placeholder(R.drawable.order_img_2)
                    .error(R.mipmap.ic_launcher)
                    .into(imageView_supplier_image);
        }

    }

    private void setOnClickListeners(){

    }


}//Class