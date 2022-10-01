package com.hacathon.heavyequipmentrent.models.Requests;

public class GetEquipmentsRequest {

    Long subCategoryId;

    public GetEquipmentsRequest() {
    }

    public Long getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(Long subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

}
