package com.hacathon.heavyequipmentrent.database;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SubCategoriesBean extends RealmObject {

    @PrimaryKey
    Long subCatId;
    Long mainCarId;
    String titleAr;
    String titleEn;
    String descriptionAr;
    String descriptionEn;
    String iconImageRaw;
    String refrenceImageRaw;

    public SubCategoriesBean() {
    }

    public Long getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(Long subCatId) {
        this.subCatId = subCatId;
    }

    public Long getMainCarId() {
        return mainCarId;
    }

    public void setMainCarId(Long mainCarId) {
        this.mainCarId = mainCarId;
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
        return iconImageRaw;
    }

    public void setIconImageRaw(String iconImageRaw) {
        this.iconImageRaw = iconImageRaw;
    }

    public String getRefrenceImageRaw() {
        return refrenceImageRaw;
    }

    public void setRefrenceImageRaw(String refrenceImageRaw) {
        this.refrenceImageRaw = refrenceImageRaw;
    }


}
