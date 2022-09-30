package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContinueOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContinueOrderFragment extends Fragment {


    View view;
    MainCallBacks mainCallBacks;


    public ContinueOrderFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static ContinueOrderFragment newInstance(MainCallBacks mainCallBacks) {
        ContinueOrderFragment fragment = new ContinueOrderFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.continue_order_fragment_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.SHOW);


    }//OnCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_continue_order, container, false);


        return view;
    }//OnCreateView


}//Class