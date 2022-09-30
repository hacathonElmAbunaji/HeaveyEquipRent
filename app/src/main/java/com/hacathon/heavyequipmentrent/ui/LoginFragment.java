package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
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
    ImageView imageView_language;
    TextInputLayout textInputLayout_username, textInputLayout_password;
    TextInputEditText editTextUsername, editTextPassword;

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
        imageView_language = view.findViewById(R.id.imageView_language);
        textInputLayout_username = view.findViewById(R.id.textInputLayout_username);
        textInputLayout_password = view.findViewById(R.id.textInputLayout_password);
        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);

    }

    public void onClickListeners(){
        imageView_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Change language", Toast.LENGTH_LONG).show();
            }
        });

        editTextUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Username clicked", Toast.LENGTH_LONG).show();
            }
        });

        editTextPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Password clicked", Toast.LENGTH_LONG).show();
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidToSubmit()){
                    ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Home);
                }
            }
        });
    }


    private boolean isValidToSubmit(){

        textInputLayout_username.setErrorEnabled(false);
        textInputLayout_password.setErrorEnabled(false);

        if (editTextUsername.getText().toString().isEmpty()){
            textInputLayout_username.setError(getString(R.string.login_valid_username_error_message));
            return false;
        }

        if (editTextUsername.getText().toString().length() < 5){
            textInputLayout_username.setError(getString(R.string.login_valid_username_length_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().isEmpty()){
            textInputLayout_password.setError(getString(R.string.login_valid_password_error_message));
            return false;
        }

        if (editTextPassword.getText().toString().length() < 8){
            textInputLayout_password.setError(getString(R.string.login_valid_password_length_error_message));
            return false;
        }

        return true;
    }





}//Class