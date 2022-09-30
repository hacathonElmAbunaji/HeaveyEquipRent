package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.EquipmentCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.EquipmentsAdapter;
import com.hacathon.heavyequipmentrent.ui.Adapters.SubCategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectEquipmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectEquipmentFragment extends Fragment implements EquipmentCallBacks {

    View view;
    MainCallBacks mainCallBacks;
    RecyclerView recycler_view_equipment;
    EquipmentsAdapter adapter;

    public SelectEquipmentFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static SelectEquipmentFragment newInstance(MainCallBacks mainCallBacks) {
        SelectEquipmentFragment fragment = new SelectEquipmentFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;

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
        view = inflater.inflate(R.layout.fragment_select_equipment, container, false);

        //Adapt
        adapter = new EquipmentsAdapter(getContext(), this);

        initLayouts();
        initTable();
        populateTable();

        return view;
    }//OnCreateView


    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.equipments_fragment_title));

        recycler_view_equipment = view.findViewById(R.id.recycler_view_equipment);


//        textView_selected_cat_name = view.findViewById(R.id.textView_selected_cat_name);
//        textView_selected_cat_name.setText(getString(R.string.menu_item_home_category));

    }



    public void initTable(){
        recycler_view_equipment.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_equipment.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_equipment.setAdapter(adapter);
    }


    @Override
    public void equipmentSelected(String name) {
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.CONTINUE_ORDER);
    }

}//Class