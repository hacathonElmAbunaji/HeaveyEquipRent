package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CategoriesBean;
import com.hacathon.heavyequipmentrent.database.EquipmentsBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.GetEquipmentsRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetSubCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Responses.GetEquipmentsResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetEquipmentsResponseObjectImage;
import com.hacathon.heavyequipmentrent.models.Responses.GetSubCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetSubCategoriesResponseObject;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.EquipmentCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.EquipmentsAdapter;
import com.hacathon.heavyequipmentrent.ui.Adapters.SubCategoryAdapter;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    TextView textView_selected_cat_name, textView_selected_sub_cat_name;
    Long selectedCatId;
    Long selectedSubCatId;
    SwipeRefreshLayout swipeLayout_equipment;

    public SelectEquipmentFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static SelectEquipmentFragment newInstance(MainCallBacks mainCallBacks, Long selectedCatId, Long selectedSubCatId) {
        SelectEquipmentFragment fragment = new SelectEquipmentFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;
        fragment.selectedCatId = selectedCatId;
        fragment.selectedSubCatId = selectedSubCatId;

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
        adapter = new EquipmentsAdapter(getContext(), selectedCatId, selectedSubCatId, this);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.equipments_fragment_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.SHOW);

        initLayouts();
        initTable();
        populateTable();
        setClickListener();

        int catSize = Realm.getDefaultInstance().where(EquipmentsBean.class).findAll().size();
        if (catSize == 0){
            GetEquipments();
        }

        return view;
    }//OnCreateView


    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.equipments_fragment_title));

        recycler_view_equipment = view.findViewById(R.id.recycler_view_equipment);

        CategoriesBean bean = Realm.getDefaultInstance().where(CategoriesBean.class).equalTo("catId", selectedCatId).findFirst();
        SubCategoriesBean beanSub = Realm.getDefaultInstance().where(SubCategoriesBean.class).equalTo("subCatId", selectedSubCatId).findFirst();

        textView_selected_cat_name = view.findViewById(R.id.textView_selected_cat_name);
        textView_selected_sub_cat_name = view.findViewById(R.id.textView_selected_sub_cat_name);
        swipeLayout_equipment = view.findViewById(R.id.swipeLayout_equipment);

        if (bean != null && bean.isValid()){
            if (beanSub != null && beanSub.isValid()){
                String cat = LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn();
                String subCat = LanguageManager.isCurrentLangARabic() ? beanSub.getTitleAr() : beanSub.getTitleEn();
                textView_selected_cat_name.setText(cat + " - " + subCat);
            }
        }
    }


    private void setClickListener(){
        swipeLayout_equipment.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetEquipments();
            }
        });
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
    public void equipmentSelected(Long equipId) {
        ((MainActivity) getActivity()).selectedEquipmentId = equipId;
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.CONTINUE_ORDER);
    }

    @Override
    public void supplierClicked(Long equipId) {
//        showSupplierBottomSheet(equipId);
    }

    private void showSupplierBottomSheet(Long equipId){
        SupplierInfoFragment supplierInfoFragment = new SupplierInfoFragment();
        if (getChildFragmentManager().findFragmentByTag("SUPPLIER_SHEET") == null){
            supplierInfoFragment.equipId = equipId;
            supplierInfoFragment.show(getChildFragmentManager(), "SUPPLIER_SHEET");
        }
    }


    private void GetEquipments(){

        if (NetworkHelper.getInstance().isConnected()){

            swipeLayout_equipment.setRefreshing(false);

            GetEquipmentsRequest req = new GetEquipmentsRequest();
            req.setSubCategoryId(selectedSubCatId);

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<GetEquipmentsResponse> cResponse = MyApplication.getRestClient().getApiService().getEquipments(req);
            cResponse.enqueue(new Callback<GetEquipmentsResponse>() {
                @Override
                public void onResponse(Call<GetEquipmentsResponse> call, final Response<GetEquipmentsResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_equipment.setRefreshing(false);
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final GetEquipmentsResponse res = response.body();
                        if (res.getCode() != null && res.getCode() == 0){
                            if( res.getItems() != null &&  res.getItems().size() > 0){
                                saveEquipmentsToRealm(res);
                            }else {
                                MyApplication.getInstance().reportError(getString(R.string.menu_item_no_categories));
                            }
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
                public void onFailure(Call<GetEquipmentsResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_equipment.setRefreshing(false);
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }



    private void saveEquipmentsToRealm(GetEquipmentsResponse response){
        Realm.getDefaultInstance().beginTransaction();

        for (int i = 0 ; i < response.getItems().size() ; i++){
            EquipmentsBean equipmentsBean = new EquipmentsBean();
            equipmentsBean.setId(response.getItems().get(i).getId());
            equipmentsBean.setMainCatId(selectedCatId);
            equipmentsBean.setSubCatId(selectedSubCatId);
            equipmentsBean.setTitleAr(response.getItems().get(i).getTitleAr());
            equipmentsBean.setTitleEn(response.getItems().get(i).getTitleEn());
            equipmentsBean.setDescriptionAr(response.getItems().get(i).getDescriptionAr());
            equipmentsBean.setDescriptionEn(response.getItems().get(i).getDescriptionEn());
            equipmentsBean.setRefrenceImageRaw(response.getItems().get(i).getRefrenceImageRaw());
            equipmentsBean.setDailyPrice(response.getItems().get(i).getDailyPrice());
            equipmentsBean.setWeeklyPrice(response.getItems().get(i).getWeeklyPrice());
            equipmentsBean.setMonthlyPrice(response.getItems().get(i).getMonthlyPrice());
            equipmentsBean.setHeight(response.getItems().get(i).getHeight());
            equipmentsBean.setQuantity(response.getItems().get(i).getQuantity());
            equipmentsBean.setWieght(response.getItems().get(i).getWieght());
            equipmentsBean.setSupplierId(response.getItems().get(i).getSupplierId());
            equipmentsBean.setSupplierNameAr(response.getItems().get(i).getSupplierNameAr());
            equipmentsBean.setSupplierNameEn(response.getItems().get(i).getSupplierNameEn());
            equipmentsBean.setSupplierlogoImage(response.getItems().get(i).getSupplierlogoImage());

            RealmList<GetEquipmentsResponseObjectImage> equipmentImages = new RealmList<>();

            for (int q = 0 ; q < response.getItems().get(i).getEquipmentImages().size() ; q++){
                GetEquipmentsResponseObjectImage objectImage = new GetEquipmentsResponseObjectImage();
                objectImage.setImageId(response.getItems().get(i).getEquipmentImages().get(q).getImageId());

                equipmentImages.add(objectImage);
            }

            if (equipmentImages.size() > 0){
                equipmentsBean.setEquipmentImages(equipmentImages);
            }

            Realm.getDefaultInstance().copyToRealmOrUpdate(equipmentsBean);
        }
        Realm.getDefaultInstance().commitTransaction();

        initTable();
        populateTable();
    }

}//Class