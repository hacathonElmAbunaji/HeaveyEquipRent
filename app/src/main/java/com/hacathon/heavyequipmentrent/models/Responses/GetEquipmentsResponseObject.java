package com.hacathon.heavyequipmentrent.models.Responses;

import java.util.List;

public class GetEquipmentsResponseObject {

    Long id;
    Long subCatId;
    String titleAr;
    String titleEn;
    String descriptionAr;
    String descriptionEn;
    Double dailyPrice;
    Double weeklyPrice;
    Double MonthlyPrice;
    String refrenceImageRaw;
    List<GetEquipmentsResponseObjectImage> equipmentImages;
    Long height;
    Long quantity;
    Double wieght;
    Long supplierId;
    String supplierNameAr;
    String supplierNameEn;
    String supplierlogoImage;



    public GetEquipmentsResponseObject() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Long subCatId) {
        this.subCatId = subCatId;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getTitleEn() {
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public Double getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Double dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public Double getWeeklyPrice() {
        return weeklyPrice;
    }

    public void setWeeklyPrice(Double weeklyPrice) {
        this.weeklyPrice = weeklyPrice;
    }

    public Double getMonthlyPrice() {
        return MonthlyPrice;
    }

    public void setMonthlyPrice(Double monthlyPrice) {
        MonthlyPrice = monthlyPrice;
    }

    public String getRefrenceImageRaw() {
        return refrenceImageRaw;
    }

    public void setRefrenceImageRaw(String refrenceImageRaw) {
        this.refrenceImageRaw = refrenceImageRaw;
    }

    public List<GetEquipmentsResponseObjectImage> getEquipmentImages() {
        return equipmentImages;
    }

    public void setEquipmentImages(List<GetEquipmentsResponseObjectImage> equipmentImages) {
        this.equipmentImages = equipmentImages;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getWieght() {
        return wieght;
    }

    public void setWieght(Double wieght) {
        this.wieght = wieght;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierNameAr() {
        return supplierNameAr;
    }

    public void setSupplierNameAr(String supplierNameAr) {
        this.supplierNameAr = supplierNameAr;
    }

    public String getSupplierNameEn() {
        return supplierNameEn;
    }

    public void setSupplierNameEn(String supplierNameEn) {
        this.supplierNameEn = supplierNameEn;
    }

    public String getSupplierlogoImage() {
        return supplierlogoImage;
    }

    public void setSupplierlogoImage(String supplierlogoImage) {
        this.supplierlogoImage = supplierlogoImage;
    }
}
