package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CategoriesBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.GetCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetSubCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Responses.GetCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetSubCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetSubCategoriesResponseObject;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.SubCatCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.SubCategoryAdapter;
import com.hacathon.heavyequipmentrent.utilis.LanguageManager;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    Long selectedCatId;
    SwipeRefreshLayout swipeLayout_sub;

    public SubCategoryFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static SubCategoryFragment newInstance(MainCallBacks mainCallBacks, Long selectedCatId) {
        SubCategoryFragment fragment = new SubCategoryFragment(mainCallBacks);
        fragment.mainCallBacks = mainCallBacks;
        fragment.selectedCatId = selectedCatId;

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
        adapter = new SubCategoryAdapter(getContext(), selectedCatId, this);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_item_home_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.SHOW);

        initLayouts();
        initTable();
        populateTable();
        setClickListeners();

        int catSize = Realm.getDefaultInstance().where(SubCategoriesBean.class).findAll().size();
        if (catSize == 0){
            GetSubCategories();
        }

        return view;
    }//OnCreateView



    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_item_home_title));

        recycler_view_categories_sub = view.findViewById(R.id.recycler_view_categories_sub);
        textView_selected_cat_name = view.findViewById(R.id.textView_selected_cat_name);
        swipeLayout_sub = view.findViewById(R.id.swipeLayout_sub);


        CategoriesBean bean = Realm.getDefaultInstance().where(CategoriesBean.class).equalTo("catId", selectedCatId).findFirst();

        textView_selected_cat_name.setText(LanguageManager.isCurrentLangARabic() ? bean.getTitleAr() : bean.getTitleEn());

    }



    public void initTable(){
        recycler_view_categories_sub.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_categories_sub.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_categories_sub.setAdapter(adapter);
    }

    public void setClickListeners(){
        swipeLayout_sub.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetSubCategories();
            }
        });
    }

    @Override
    public void subCategoryItemClicked(Long subCatId) {
        ((MainActivity) getActivity()).selectedSubCatId = subCatId;
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.SELECT_EQUIPMENT);
    }


    private void GetSubCategories(){
        if (NetworkHelper.getInstance().isConnected()){

            swipeLayout_sub.setRefreshing(false);

            GetSubCategoriesRequest req = new GetSubCategoriesRequest();
            req.setMainCategoryId(selectedCatId);

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<GetSubCategoriesResponse> cResponse = MyApplication.getRestClient().getApiService().getSubCategories(req);
            cResponse.enqueue(new Callback<GetSubCategoriesResponse>() {
                @Override
                public void onResponse(Call<GetSubCategoriesResponse> call, final Response<GetSubCategoriesResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_sub.setRefreshing(false);
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final GetSubCategoriesResponse res = response.body();
                        if (res.getCode() != null && res.getCode() == 0){
                            if( res.getList() != null &&  res.getList().size() > 0){
                                saveSubCategoriesToRealm(res);
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
                public void onFailure(Call<GetSubCategoriesResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_sub.setRefreshing(false);
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }


    private void saveSubCategoriesToRealm(GetSubCategoriesResponse response){
        Realm.getDefaultInstance().beginTransaction();

        for (int i = 0 ; i < response.getList().size() ; i++){
            SubCategoriesBean cat = new SubCategoriesBean();
            cat.setSubCatId(response.getList().get(i).getId());
            cat.setMainCarId(selectedCatId);
            cat.setTitleAr(response.getList().get(i).getTitleAr());
            cat.setTitleEn(response.getList().get(i).getTitleEn());
            cat.setDescriptionAr(response.getList().get(i).getDescriptionAr());
            cat.setDescriptionEn(response.getList().get(i).getDescriptionEn());
            cat.setRefrenceImageRaw(response.getList().get(i).getRefrenceImage());

            Realm.getDefaultInstance().copyToRealmOrUpdate(cat);
        }
        Realm.getDefaultInstance().commitTransaction();

        initTable();
        populateTable();
    }

}//Class