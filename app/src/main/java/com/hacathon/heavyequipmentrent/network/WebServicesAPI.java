package com.hacathon.heavyequipmentrent.network;

import com.hacathon.heavyequipmentrent.models.Requests.CreateOrderRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetEquipmentsRequest;
import com.hacathon.heavyequipmentrent.models.Requests.GetSubCategoriesRequest;
import com.hacathon.heavyequipmentrent.models.Requests.LoginRequest;
import com.hacathon.heavyequipmentrent.models.Responses.CreateOrderResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetEquipmentsResponse;
import com.hacathon.heavyequipmentrent.models.Responses.GetSubCategoriesResponse;
import com.hacathon.heavyequipmentrent.models.Responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebServicesAPI {


    @POST(WebServicesURLS.KWebLogin)
    Call<LoginResponse> loginService(@Body LoginRequest req);

    @POST(WebServicesURLS.KWebCategories)
    Call<GetCategoriesResponse> getCategories(@Body GetCategoriesRequest req);

    @POST(WebServicesURLS.KWebSubCategories)
    Call<GetSubCategoriesResponse> getSubCategories(@Body GetSubCategoriesRequest req);

    @POST(WebServicesURLS.KWebEquipments)
    Call<GetEquipmentsResponse> getEquipments(@Body GetEquipmentsRequest req);

    @POST(WebServicesURLS.KWebCreateOrder)
    Call<CreateOrderResponse> createOrders(@Body CreateOrderRequest req);

}
