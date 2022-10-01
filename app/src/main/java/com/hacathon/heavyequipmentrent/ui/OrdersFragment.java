package com.hacathon.heavyequipmentrent.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hacathon.heavyequipmentrent.Constants.Constants;
import com.hacathon.heavyequipmentrent.MainActivity;
import com.hacathon.heavyequipmentrent.R;
import com.hacathon.heavyequipmentrent.appcore.MyApplication;
import com.hacathon.heavyequipmentrent.database.CreateOrderBean;
import com.hacathon.heavyequipmentrent.database.SubCategoriesBean;
import com.hacathon.heavyequipmentrent.database.UserBean;
import com.hacathon.heavyequipmentrent.models.Requests.GetMyRfpsRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetSubCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Requests.SubmitOrderRequest;
import com.hacathon.heavyequipmentrent.models.Responses.GetMyRfpsResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetMyRfpsResponse;
import com.hacathon.heavyequipmentrent.models.Responses.SubmitOrderResponse;
import com.hacathon.heavyequipmentrent.network.NetworkHelper;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.MainCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.CallBacks.OrderCallBacks;
import com.hacathon.heavyequipmentrent.ui.Adapters.OrdersAdapter;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrdersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrdersFragment extends Fragment implements OrderCallBacks {


    View view;
    OrdersAdapter adapter;
    RecyclerView recycler_view_orders;
    MainCallBacks mainCallBacks;
    SwipeRefreshLayout swipeLayout_orders;

    public OrdersFragment(MainCallBacks mainCallBacks) {
        mainCallBacks = mainCallBacks;
    }


    public static OrdersFragment newInstance(MainCallBacks mainCallBacks) {
        OrdersFragment fragment = new OrdersFragment(mainCallBacks);
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
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        //ACTIVITY CUSTOM
        ((MainActivity) getActivity()).showHideBottomNavBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.cell_orders_title));
        ((MainActivity) getActivity()).showHideTopBackButton(Constants.ShowOrHide.HIDE);

        //Adapt
        adapter = new OrdersAdapter(getActivity(), this);


        initLayouts();
        initTable();
        populateTable();
        setOnClickListeners();


        int ordersSize = Realm.getDefaultInstance().where(CreateOrderBean.class).findAll().size();
        if (ordersSize == 0){
            GetOrders();
        }


        return view;
    }//OnCreateView

    public void initLayouts(){

        ((MainActivity) getActivity()).showHideTopActionBar(Constants.ShowOrHide.SHOW);
        ((MainActivity) getActivity()).setActionBarTitle(getString(R.string.cell_orders_title));

        recycler_view_orders = view.findViewById(R.id.recycler_view_orders);
        swipeLayout_orders = view.findViewById(R.id.swipeLayout_orders);
    }

    private void setOnClickListeners(){
        swipeLayout_orders.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                GetOrders();
            }
        });
    }



    public void initTable(){
        recycler_view_orders.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recycler_view_orders.setLayoutManager(layoutManager);
    }

    public void populateTable(){
        recycler_view_orders.setAdapter(adapter);
    }



    private void GetOrders(){
        if (NetworkHelper.getInstance().isConnected()){

            swipeLayout_orders.setRefreshing(false);

            UserBean bean = Realm.getDefaultInstance().where(UserBean.class).findFirst();
            GetMyRfpsRequest req = new GetMyRfpsRequest();

            if (bean != null && bean.isValid()){
                req.setUserId(bean.getUserID());
            }

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<GetMyRfpsResponse> cResponse = MyApplication.getRestClient().getApiService().getMyRFPs(req);
            cResponse.enqueue(new Callback<GetMyRfpsResponse>() {
                @Override
                public void onResponse(Call<GetMyRfpsResponse> call, final Response<GetMyRfpsResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_orders.setRefreshing(false);
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final GetMyRfpsResponse res = response.body();
                        if (res.getResponseCode() != null && res.getResponseCode() == 0){
                            if( res.getItems() != null &&  res.getItems().size() > 0){
                                saveOrdersToRealm(res);
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
                public void onFailure(Call<GetMyRfpsResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_orders.setRefreshing(false);
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }



    private void saveOrdersToRealm(GetMyRfpsResponse response){
        Realm.getDefaultInstance().beginTransaction();

        for (int i = 0 ; i < response.getItems().size() ; i++){
            CreateOrderBean bean = new CreateOrderBean();
            bean.setId(response.getItems().get(i).getId());
            bean.setState(response.getItems().get(i).getState());
            bean.setRenToDate(response.getItems().get(i).getRenToDate());
            bean.setRentStartDate(response.getItems().get(i).getRentStartDate());
            bean.setProjectLocation(response.getItems().get(i).getProjectLocation());
            bean.setCatalogSubCategoryId(response.getItems().get(i).getCatalogSubCategoryId());
            bean.setCategoryTitleAr(response.getItems().get(i).getCategoryTitleAr());
            bean.setCategoryTitleEn(response.getItems().get(i).getCategoryTitleEn());
            bean.setProjectDescription(response.getItems().get(i).getProjectDescription());
            bean.setSubCategoryTitleAr(response.getItems().get(i).getSubCategoryTitleAr());
            bean.setSubCategoryTitleEn(response.getItems().get(i).getSubCategoryTitleEn());
            bean.setImage(response.getItems().get(i).getImage());

            Realm.getDefaultInstance().copyToRealmOrUpdate(bean);
        }
        Realm.getDefaultInstance().commitTransaction();

        initTable();
        populateTable();
    }


    @Override
    public void ratingSelected(Long orderId, float rate) {
        SubmitRating(orderId, rate);
    }



    private void SubmitRating(Long orderId, float rate){
        if (NetworkHelper.getInstance().isConnected()){

            swipeLayout_orders.setRefreshing(false);

            UserBean bean = Realm.getDefaultInstance().where(UserBean.class).findFirst();

            SubmitOrderRequest req = new SubmitOrderRequest();
            if (bean != null && bean.isValid()){
                req.setUserId(bean.getUserID());
            }
            req.setRatingValue((int) rate);
            req.setPropsalId(orderId);

            ((MainActivity) getActivity()).showLoadingDialog(null, null);

            Call<SubmitOrderResponse> cResponse = MyApplication.getRestClient().getApiService().submitRatingOrder(req);
            cResponse.enqueue(new Callback<SubmitOrderResponse>() {
                @Override
                public void onResponse(Call<SubmitOrderResponse> call, final Response<SubmitOrderResponse> response) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_orders.setRefreshing(false);
                    }
                    if (response !=null && response.errorBody() == null && response.body() != null){
                        final SubmitOrderResponse res = response.body();
                        if (res.getResponseCode() != null && res.getResponseCode() == 0){
                            MyApplication.getInstance().reportError(getString(R.string.cell_orders_order_rating_submitted));
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
                public void onFailure(Call<SubmitOrderResponse> call, Throwable t) {
                    if (isVisible() && !isDetached()){
                        ((MainActivity) getActivity()).hideLoadingDialog();
                        swipeLayout_orders.setRefreshing(false);
                        MyApplication.getInstance().reportError(getString(R.string.server_error));
                    }
                }
            });
        }else {
            MyApplication.getInstance().reportError(getString(R.string.check_connection));
        }
    }


}//Class