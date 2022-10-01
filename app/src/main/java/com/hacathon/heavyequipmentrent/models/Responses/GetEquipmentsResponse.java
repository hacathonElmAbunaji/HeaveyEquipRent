package com.hacathon.heavyequipmentrent.models.Responses;

import java.util.List;

public class GetEquipmentsResponse {

    Long responseCode;
    List<GetEquipmentsResponseObject> items;

    public GetEquipmentsResponse() {
    }

    public Long getCode() {
        return responseCode;
    }

    public void setCode(Long code) {
        this.responseCode = code;
    }

    public List<GetEquipmentsResponseObject> getItems() {
        return items;
    }

    public void setItems(List<GetEquipmentsResponseObject> items) {
        this.items = items;
    }
}
