package com.hacathon.heavyequipmentrent.models.Requests;

public class GetSubCategoriesRequest {

    Long mainCategoryId;

    public GetSubCategoriesRequest() {
    }

    public Long getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Long mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }

}
