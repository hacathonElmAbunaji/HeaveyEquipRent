package com.hacathon.heavyequipmentrent.models.Responses;

public class GetSubCategoriesResponseObject {


    Long id;
    Long mainCatID;
    String titleAr;
    String titleEn;
    String descriptionAr;
    String descriptionEn;
    String refrenceImage;


    public GetSubCategoriesResponseObject() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainCatID() {
        return mainCatID;
    }

    public void setMainCatID(Long mainCatID) {
        this.mainCatID = mainCatID;
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

    public String getRefrenceImage() {
        return refrenceImage;
    }

    public void setRefrenceImage(String refrenceImage) {
        this.refrenceImage = refrenceImage;
    }

}
