package com.hacathon.heavyequipmentrent.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;

import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContinueOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContinueOrderFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    View view;
    MainCallBacks mainCallBacks;
    Button button_submit_order;
    TextInputLayout textInputLayout_date_from, textInputLayout_date_to;
    TextInputEditText editTextDateFrom, editTextDateTo;
    private GoogleMap mMap;
    private int mYear, mMonth, mDay;
    private long dateFrom;
    private long dateTo;


    MarkerOptions mapMarker;

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
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);


    }//OnCreate

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_continue_order, container, false);

        initLayouts();
        onClickListeners();
        initGoogleMap();

        return view;
    }//OnCreateView


    private void initLayouts(){
        button_submit_order = view.findViewById(R.id.button_submit_order);
        textInputLayout_date_from = view.findViewById(R.id.textInputLayout_date_from);
        textInputLayout_date_to = view.findViewById(R.id.textInputLayout_date_to);
        editTextDateFrom = view.findViewById(R.id.editTextDateFrom);
        editTextDateTo = view.findViewById(R.id.editTextDateTo);
    }

    private void initGoogleMap(){
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager()
                .findFragmentById(R.id.google_map_view);
        if (mapFragment != null){
            mapFragment.getMapAsync(this);
        }
    }

    private void onClickListeners(){
        editTextDateFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), new DatePickerDialog.OnDateSetListener () {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        dateFrom = calendar.getTimeInMillis();

                        editTextDateFrom.setText (dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });

        editTextDateTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance ();
                mYear = calendar.get ( Calendar.YEAR );
                mMonth = calendar.get ( Calendar.MONTH );
                mDay = calendar.get ( Calendar.DAY_OF_MONTH );
                //show dialog
                DatePickerDialog datePickerDialog = new DatePickerDialog ( getContext(), new DatePickerDialog.OnDateSetListener () {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, dayOfMonth);
                        dateTo = calendar.getTimeInMillis();

                        editTextDateTo.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.show ();
            }
        });

        button_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidToSubmit()){
                    Toast.makeText(getContext(), "Submit order now", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng riyadh = new LatLng(24.732416, 46.697525);
        CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                riyadh, 9);
        mMap.animateCamera(location);



        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                if (mapMarker != null && mapMarker.getPosition() != latLng){
                    mMap.clear();
                }

                mapMarker = new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(getString(R.string.continue_order_fragment_map_customer_location));

                mMap.addMarker(mapMarker);
            }
        });
    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        if (marker.equals(mapMarker)) {
            mapMarker = new MarkerOptions().position(marker.getPosition()).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).title(getString(R.string.continue_order_fragment_map_customer_location));
        }
        return false;
    }


    private boolean isValidToSubmit(){

        textInputLayout_date_from.setErrorEnabled(false);
        textInputLayout_date_to.setErrorEnabled(false);

        if (editTextDateFrom.getText().toString().isEmpty()){
            textInputLayout_date_from.setError(getString(R.string.continue_order_fragment_error_message_date_from));
            return false;
        }

        if (editTextDateTo.getText().toString().isEmpty()){
            textInputLayout_date_to.setError(getString(R.string.continue_order_fragment_error_message_date_to));
            return false;
        }

        if (mapMarker == null || mapMarker.getPosition().latitude == 0.0 || mapMarker.getPosition().longitude == 0.0){
            Toast.makeText(getContext(), getString(R.string.continue_order_fragment_error_message_location_on_map), Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }



}//Class