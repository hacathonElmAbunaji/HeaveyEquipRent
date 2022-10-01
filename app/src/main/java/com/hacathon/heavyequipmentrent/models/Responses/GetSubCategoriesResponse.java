package com.hacathon.heavyequipmentrent.models.Responses;

import java.util.List;

public class GetSubCategoriesResponse {


    Long responseCode;
    List<GetSubCategoriesResponseObject> items;

    public GetSubCategoriesResponse() {
    }

    public Long getCode() {
        return responseCode;
    }

    public void setCode(Long code) {
        this.responseCode = code;
    }

    public List<GetSubCategoriesResponseObject> getList() {
        return items;
    }

    public void setList(List<GetSubCategoriesResponseObject> list) {
        this.items = list;
    }
}
