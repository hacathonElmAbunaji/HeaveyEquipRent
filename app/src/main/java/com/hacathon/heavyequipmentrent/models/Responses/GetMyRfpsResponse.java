package com.hacathon.heavyequipmentrent.models.Responses;

import java.util.List;

public class GetMyRfpsResponse {
    Long responseCode;
    List<GetMyRfpsResponseObject> items;

    public Long getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Long responseCode) {
        this.responseCode = responseCode;
    }

    public List<GetMyRfpsResponseObject> getItems() {
        return items;
    }

    public void setItems(List<GetMyRfpsResponseObject> items) {
        this.items = items;
    }
}
