package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CategoriesBean;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.GetCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Requests.LoginRequest;
import com.hacathon.heavyequipmentrent.models.Responses.GetCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.LoginResponse;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.HomeCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.HomeCategoriesAdapter;
import com.hacathon.heavyequipmentrent.ui.Adapters.OrdersAdapter;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    SwipeRefreshLayout swipeLayout;
    ImageView img_place_holder;

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
        setClickListeners();

        int catSize = Realm.getDefaultInstance().where(CategoriesBean.class).findAll().size();
        if (catSize == 0){
            GetCategories();
        }

        return view;
    }//OnCreateView

    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.menu_item_home_title));

        recycler_view_categories = view.findViewById(R.id.recycler_view_categories);
        swipeLayout = view.findViewById(R.id.swipeLayout);
    }



    public void initTable(){
        recycler_view_categories.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_categories.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_categories.setAdapter(adapter);
    }

    public void setClickListeners(){
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetCategories();
            }
        });
    }

    @Override
    public void categoryItemClicked(Long catId) {
        ((MainActivity) getActivity()).selectedCatId = catId;
        ((MainActivity) getActivity()).navigateTo(Constants.Navigations.SUB_CATEGORY);
    }



    private void GetCategories(){

        if (NetworkHelper.getInstance().isConnected()){

            swipeLayout.setRefreshing(false);

            GetCategoriesRequest req = new GetCategoriesRequest();

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<GetCategoriesResponse> cResponse = MyApplication.getRestClient().getApiService().getCategories(req);
            cResponse.enqueue(new Callback<GetCategoriesResponse>() {
                @Override
                public void onResponse(Call<GetCategoriesResponse> call, final Response<GetCategoriesResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout.setRefreshing(false);
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final GetCategoriesResponse res = response.body();
                        if (res.getCode() != null && res.getCode() == 0){
                            if( res.getList() != null &&  res.getList().size() > 0){
                                saveCategoriesToRealm(res);
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
                public void onFailure(Call<GetCategoriesResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout.setRefreshing(false);
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }



    private void saveCategoriesToRealm(GetCategoriesResponse response){
        Realm.getDefaultInstance().beginTransaction();
        for (int i = 0 ; i < response.getList().size() ; i++){
            CategoriesBean cat = new CategoriesBean();
            cat.setCatId(response.getList().get(i).getId());
            cat.setTitleAr(response.getList().get(i).getTitleAr());
            cat.setTitleEn(response.getList().get(i).getTitleEn());
            cat.setDescriptionAr(response.getList().get(i).getDescriptionAr());
            cat.setDescriptionEn(response.getList().get(i).getDescriptionEn());
            cat.setIconImageRaw(response.getList().get(i).getIconImageRaw());
            cat.setRefrenceImageRaw(response.getList().get(i).getRefrenceImageRaw());

            Realm.getDefaultInstance().copyToRealmOrUpdate(cat);
        }
        Realm.getDefaultInstance().commitTransaction();

        initTable();
        populateTable();
    }



}//Class