package com.hacathon.heavyequipmentrent.database;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class CategoriesBean extends RealmObject {

    @PrimaryKey
    Long catId;
    String titleAr;
    String titleEn;
    String descriptionAr;
    String descriptionEn;
    String iconImageRaw;
    String refrenceImageRaw;


    public CategoriesBean() {
    }

    public Long getCatId() {
        return catId;
    }

    public void setCatId(Long catId) {
        this.catId = catId;
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
