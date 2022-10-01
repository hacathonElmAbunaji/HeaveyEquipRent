package com.hacathon.heavyequipmentrent.models.Responses;

public class GetCategoriesResponseObject {

    Long id;
    String titleAr;
    String titleEn;
    String descriptionAr;
    String descriptionEn;
    String refrenceImage;
    String iconImage;

    public GetCategoriesResponseObject() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getIconImageRaw() {
        return iconImage;
    }

    public void setIconImageRaw(String iconImageRaw) {
        this.iconImage = iconImageRaw;
    }

    public String getRefrenceImageRaw() {
        return refrenceImage;
    }

    public void setRefrenceImageRaw(String refrenceImageRaw) {
        this.refrenceImage = refrenceImageRaw;
    }

}
