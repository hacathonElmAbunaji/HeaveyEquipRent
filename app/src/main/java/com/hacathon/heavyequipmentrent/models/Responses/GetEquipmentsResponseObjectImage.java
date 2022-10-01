package com.hacathon.heavyequipmentrent.models.Responses;

import io.realm.RealmObject;

public class GetEquipmentsResponseObjectImage extends RealmObject {

    String imageId;

    public GetEquipmentsResponseObjectImage() {
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
