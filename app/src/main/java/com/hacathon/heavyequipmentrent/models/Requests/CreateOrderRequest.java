package com.hacathon.heavyequipmentrent.models.Requests;

import java.util.Date;

public class CreateOrderRequest {

    Long UserId;
    Long CityId;
    Long CatalogSubCategoryId;
    Long RentStartDate;
    Long RentToDate;
    Long DeadLineDate;
    String ProjectDescription;
    String ProjectLocation;



    public CreateOrderRequest() {
    }

    public Long getCatalogSubCategoryId() {
        return CatalogSubCategoryId;
    }

    public void setCatalogSubCategoryId(Long catalogSubCategoryId) {
        CatalogSubCategoryId = catalogSubCategoryId;
    }

    public Long getCityId() {
        return CityId;
    }

    public void setCityId(Long cityId) {
        CityId = cityId;
    }

    public Long getCreatedByUserId() {
        return UserId;
    }

    public void setCreatedByUserId(Long createdByUserId) {
        UserId = createdByUserId;
    }

    public Long getRentStartDate() {
        return RentStartDate;
    }

    public void setRentStartDate(Long rentStartDate) {
        RentStartDate = rentStartDate;
    }

    public Long getRentToDate() {
        return RentToDate;
    }

    public void setRentToDate(Long rentToDate) {
        RentToDate = rentToDate;
    }

    public Long getDeadLineDate() {
        return DeadLineDate;
    }

    public void setDeadLineDate(Long deadLineDate) {
        DeadLineDate = deadLineDate;
    }

    public String getProjectDescription() {
        return ProjectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        ProjectDescription = projectDescription;
    }

    public String getProjectLocation() {
        return ProjectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        ProjectLocation = projectLocation;
    }
}
