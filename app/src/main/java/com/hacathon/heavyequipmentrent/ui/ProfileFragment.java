package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {


    View view;
    MainCallBacks mainCallBacks;
    ImageView imageView_language, imageView_logout;

    public ProfileFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static ProfileFragment newInstance(MainCallBacks mainCallBacks) {
        ProfileFragment fragment = new ProfileFragment(mainCallBacks);
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        initLayouts();
        onClickListeners();


        return view;
    }//OnCreateView

    private void initLayouts(){

        imageView_language = view.findViewById(R.id.imageView_language);
        imageView_logout = view.findViewById(R.id.imageView_logout);
    }

    private void onClickListeners(){
        imageView_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Change language", Toast.LENGTH_LONG).show();
            }
        });

        imageView_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_LONG).show();
            }
        });
    }




}//Class