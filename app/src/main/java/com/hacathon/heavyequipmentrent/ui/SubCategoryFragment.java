package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.SubCatCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.SubCategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SubCategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubCategoryFragment extends Fragment implements SubCatCallBacks {

    View view;
    RecyclerView recycler_view_categories_sub;
    SubCategoryAdapter adapter;
    TextView textView_selected_cat_name;
    MainCallBacks mainCallBacks;

    public SubCategoryFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static SubCategoryFragment newInstance(MainCallBacks mainCallBacks) {
        SubCategoryFragment fragment = new SubCategoryFragment(mainCallBacks);
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
        view = inflater.inflate(R.layout.fragment_sub_category, container, false);


        //Adapt
        adapter = new SubCategoryAdapter(getContext(), this);

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

        recycler_view_categories_sub = view.findViewById(R.id.recycler_view_categories_sub);
        textView_selected_cat_name = view.findViewById(R.id.textView_selected_cat_name);

        textView_selected_cat_name.setText(getString(R.string.menu_item_home_category));

    }



    public void initTable(){
        recycler_view_categories_sub.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_categories_sub.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_categories_sub.setAdapter(adapter);
    }


    @Override
    public void subCategoryItemClicked(String name) {
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.SELECT_EQUIPMENT);
    }

}//Class