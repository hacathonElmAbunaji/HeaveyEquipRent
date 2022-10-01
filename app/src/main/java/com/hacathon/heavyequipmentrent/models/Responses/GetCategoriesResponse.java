package com.hacathon.heavyequipmentrent.models.Responses;

import java.util.List;

public class GetCategoriesResponse {

    Long responseCode;
    List<GetCategoriesResponseObject> items;

    public GetCategoriesResponse() {
    }

    public Long getCode() {
        return responseCode;
    }

    public void setCode(Long code) {
        this.responseCode = code;
    }

    public List<GetCategoriesResponseObject> getList() {
        return items;
    }

    public void setList(List<GetCategoriesResponseObject> list) {
        this.items = list;
    }
}
