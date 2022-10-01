package com.hacathon.heavyequipmentrent.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CreateOrderBean extends RealmObject {

    @PrimaryKey
    Long id;
    Long state;
    String renToDate;
    String rentStartDate;
    String projectLocation;
    Long catalogSubCategoryId;
    String subCategoryTitleAr;
    String subCategoryTitleEn;
    String categoryTitleAr;
    String categoryTitleEn;
    String projectDescription;
    String image;

    public CreateOrderBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long rfpId) {
        this.id = rfpId;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getRenToDate() {
        return renToDate;
    }

    public void setRenToDate(String renToDate) {
        this.renToDate = renToDate;
    }

    public String getRentStartDate() {
        return rentStartDate;
    }

    public void setRentStartDate(String rentStartDate) {
        this.rentStartDate = rentStartDate;
    }

    public String getProjectLocation() {
        return projectLocation;
    }

    public void setProjectLocation(String projectLocation) {
        this.projectLocation = projectLocation;
    }

    public Long getCatalogSubCategoryId() {
        return catalogSubCategoryId;
    }

    public void setCatalogSubCategoryId(Long catalogSubCategoryId) {
        this.catalogSubCategoryId = catalogSubCategoryId;
    }

    public String getSubCategoryTitleAr() {
        return subCategoryTitleAr;
    }

    public void setSubCategoryTitleAr(String subCategoryTitleAr) {
        this.subCategoryTitleAr = subCategoryTitleAr;
    }

    public String getSubCategoryTitleEn() {
        return subCategoryTitleEn;
    }

    public void setSubCategoryTitleEn(String subCategoryTitleEn) {
        this.subCategoryTitleEn = subCategoryTitleEn;
    }

    public String getCategoryTitleAr() {
        return categoryTitleAr;
    }

    public void setCategoryTitleAr(String categoryTitleAr) {
        this.categoryTitleAr = categoryTitleAr;
    }

    public String getCategoryTitleEn() {
        return categoryTitleEn;
    }

    public void setCategoryTitleEn(String categoryTitleEn) {
        this.categoryTitleEn = categoryTitleEn;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
