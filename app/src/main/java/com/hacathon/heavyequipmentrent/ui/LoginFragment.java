package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {


    View view;
    MainCallBacks mainCallBacks;
    Button button_login;

    public LoginFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static LoginFragment newInstance(MainCallBacks mainCallBacks) {
        LoginFragment fragment = new LoginFragment(mainCallBacks);

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
        view = inflater.inflate(R.layout.fragment_login, container, false);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.HIDE);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.HIDE);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.login_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);


        initLayouts();
        onClickListeners();


        return view;
    }//OnCreateView


    public void initLayouts(){

        button_login = view.findViewById(R.id.button_login);
    }

    public void onClickListeners(){
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Home);
            }
        });
    }




}//Class