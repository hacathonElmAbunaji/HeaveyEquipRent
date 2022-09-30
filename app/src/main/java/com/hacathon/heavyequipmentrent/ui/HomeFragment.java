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
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.HomeCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.HomeCategoriesAdapter;
import com.hacathon.heavyequipmentrent.ui.Adapters.OrdersAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements HomeCallBacks {


    View view;
    HomeCategoriesAdapter adapter;
    RecyclerView recycler_view_categories;
    MainCallBacks mainCallBacks;

    public HomeFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static HomeFragment newInstance(MainCallBacks mainCallBacks) {
        HomeFragment fragment = new HomeFragment(mainCallBacks);
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
        view = inflater.inflate(R.layout.fragment_home, container, false);

        //Adapt
        adapter = new HomeCategoriesAdapter(getContext(), this);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_item_home_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);


        initLayouts();
        initTable();
        populateTable();

        return view;
    }//OnCreateView

    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_item_home_title));

        recycler_view_categories = view.findViewById(R.id.recycler_view_categories);
    }



    public void initTable(){
        recycler_view_categories.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_categories.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_categories.setAdapter(adapter);
    }


    @Override
    public void categoryItemClicked(String name) {
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.SUB_CATEGORY);
    }


}//Class