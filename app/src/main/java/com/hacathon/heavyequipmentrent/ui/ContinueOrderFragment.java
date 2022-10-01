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
import android.widget.TextView;
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
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CategoriesBean;
import com.hacathon.heavyequipmentrent.database.EquipmentsBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.CreateOrderRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetEquipmentsRequest;
import com.hacathon.heavyequipmentrent.models.Responses.CreateOrderResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetEquipmentsResponse;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContinueOrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContinueOrderFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    View view;
    MainCallBacks mainCallBacks;
    Button button_submit_order;
    TextInputLayout textInputLayout_date_from, textInputLayout_date_to, textInputLayout_details, textInputLayout_dead_line;
    TextInputEditText editTextDateFrom, editTextDateTo, textInputEditText_details, editTextDeadLine;
    TextView textView_header_title_all_choiced;
    private GoogleMap mMap;
    private int mYear, mMonth, mDay;
    private Long dateFrom;
    private Long dateTo;
    private Long deadLine;
    Long selectedCatId;
    Long selectedSubCatId;
    Long selectedEquipmentId;

    Long fromDateLong;
    Long toDateLong;
    Long deadLineLong;

    MarkerOptions mapMarker;

    public ContinueOrderFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static ContinueOrderFragment newInstance(MainCallBacks mainCallBacks, Long selectedCatId, Long selectedSubCatId, Long selectedEquipmentId) {
        ContinueOrderFragment fragment = new ContinueOrderFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;
        fragment.selectedCatId = selectedCatId;
        fragment.selectedSubCatId = selectedSubCatId;
        fragment.selectedEquipmentId = selectedEquipmentId;

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

        initLayouts();
        onClickListeners();
        initGoogleMap();


        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.equipments_fragment_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.SHOW);


        CategoriesBean bean = Realm.getDefaultInstance().where(CategoriesBean.class).equalTo("catId", selectedCatId).findFirst();
        SubCategoriesBean beanSub = Realm.getDefaultInstance().where(SubCategoriesBean.class).equalTo("subCatId", selectedSubCatId).findFirst();
        EquipmentsBean equipBean = Realm.getDefaultInstance().where(EquipmentsBean.class).equalTo("id", selectedEquipmentId).findFirst();


        if (bean != null && bean.isValid()){
            if (beanSub != null && beanSub.isValid()){
                if (equipBean != null && equipBean.isValid()){
                    String cat = LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn();
                    String subCat = LanguageManager.isCurrentLangARabic() ? beanSub.getTitleAr() : beanSub.getTitleEn();
                    String equip = LanguageManager.isCurrentLangARabic() ? equipBean.getTitleAr() : equipBean.getTitleEn();
                    textView_header_title_all_choiced.setText(cat + " - " + subCat + " - "+ equip);
                }
            }
        }



        return view;
    }//OnCreateView


    private void initLayouts(){
        button_submit_order = view.findViewById(R.id.button_submit_order);
        textInputLayout_date_from = view.findViewById(R.id.textInputLayout_date_from);
        textInputLayout_date_to = view.findViewById(R.id.textInputLayout_date_to);
        editTextDateFrom = view.findViewById(R.id.editTextDateFrom);
        editTextDateTo = view.findViewById(R.id.editTextDateTo);
        textView_header_title_all_choiced = view.findViewById(R.id.textView_header_title_all_choiced);
        textInputLayout_details = view.findViewById(R.id.textInputLayout_details);
        textInputEditText_details = view.findViewById(R.id.textInputEditText_details);
        textInputLayout_dead_line = view.findViewById(R.id.textInputLayout_dead_line);
        editTextDeadLine = view.findViewById(R.id.editTextDeadLine);
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

                        Locale locale = new Locale("en");
                        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd",locale);
                        fromDateLong = Long.valueOf(dt.format(new Date(dateFrom)));
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                if (dateTo != null){
                    datePickerDialog.getDatePicker().setMaxDate(dateTo);
                }

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

                        Locale locale = new Locale("en");
                        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd",locale);
                        toDateLong = Long.valueOf(dt.format(new Date(dateTo)));
                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                if (dateFrom != null){
                    datePickerDialog.getDatePicker().setMinDate(dateFrom);
                }
                datePickerDialog.show ();
            }
        });

        editTextDeadLine.setOnClickListener(new View.OnClickListener() {
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
                        deadLine = calendar.getTimeInMillis();

                        editTextDeadLine.setText(dayOfMonth + "/" + (month + 1) + "/" + year);


                        Locale locale = new Locale("en");
                        SimpleDateFormat dt = new SimpleDateFormat("yyyyMMdd",locale);
                        deadLineLong = Long.valueOf(dt.format(new Date(deadLine)));

                    }
                }, mYear, mMonth, mDay );
                datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
                if (dateFrom != null){
                    datePickerDialog.getDatePicker().setMaxDate(dateFrom);
                }
                datePickerDialog.show();
            }
        });

        button_submit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidToSubmit()){
                    createOrder();
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
        textInputLayout_dead_line.setErrorEnabled(false);
        textInputLayout_details.setErrorEnabled(false);

        if (editTextDateFrom.getText().toString().isEmpty()){
            textInputLayout_date_from.setError(getString(R.string.continue_order_fragment_error_message_date_from));
            return false;
        }

        if (editTextDateTo.getText().toString().isEmpty()){
            textInputLayout_date_to.setError(getString(R.string.continue_order_fragment_error_message_date_to));
            return false;
        }

        if (editTextDeadLine.getText().toString().isEmpty()){
            textInputLayout_dead_line.setError(getString(R.string.continue_order_fragment_error_message_dead_line));
            return false;
        }

        if (mapMarker == null || mapMarker.getPosition().latitude == 0.0 || mapMarker.getPosition().longitude == 0.0){
            Toast.makeText(getContext(), getString(R.string.continue_order_fragment_error_message_location_on_map), Toast.LENGTH_LONG).show();
            return false;
        }

        if (textInputEditText_details.getText().toString().isEmpty()){
            textInputLayout_details.setError(getString(R.string.continue_order_fragment_error_details));
            return false;
        }

        return true;
    }


    private void createOrder(){

        if (NetworkHelper.getInstance().isConnected()){

            UserBean userBean = Realm.getDefaultInstance().where(UserBean.class).findFirst();

            CreateOrderRequest req = new CreateOrderRequest();
            if (userBean != null && userBean.isValid()){
                req.setCreatedByUserId(selectedSubCatId);
            }

            req.setCityId(0L);
            req.setCatalogSubCategoryId(selectedSubCatId);
            req.setRentStartDate(fromDateLong);
            req.setRentToDate(toDateLong);
            req.setDeadLineDate(deadLineLong);
            req.setProjectDescription(textInputEditText_details.getText().toString());
            req.setProjectLocation(mapMarker.getPosition().latitude+","+mapMarker.getPosition().longitude);


            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<CreateOrderResponse> cResponse = MyApplication.getRestClient().getApiService().createOrders(req);
            cResponse.enqueue(new Callback<CreateOrderResponse>() {
                @Override
                public void onResponse(Call<CreateOrderResponse> call, final Response<CreateOrderResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final CreateOrderResponse res = response.body();
                        if (res.getResponseCode() != null && res.getResponseCode() == 0){
                            ((MainActivity) getActivity()).clearBackStack();
                            ((MainActivity) getActivity()).navigateTo(Constants.Navigations.Orders);
                        }
                        else {
                            MyApplication.getInstance().reportError(getString(R.string.error_happened));
                        }
                    }
                    else {
                        MyApplication.getInstance().reportError(getString(R.string.error_serverconn));
                    }
                }
                @Override
                public void onFailure(Call<CreateOrderResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }



}//Class