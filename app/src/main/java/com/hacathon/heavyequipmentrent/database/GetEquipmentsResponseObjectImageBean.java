package com.hacathon.heavyequipmentrent.database;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class GetEquipmentsResponseObjectImageBean extends RealmObject {


    RealmList<GetEquipmentsResponseObjectImageBean> equipmentImages;

    public GetEquipmentsResponseObjectImageBean() {
    }

    public RealmList<GetEquipmentsResponseObjectImageBean> getEquipmentImages() {
        return equipmentImages;
    }

    public void setEquipmentImages(RealmList<GetEquipmentsResponseObjectImageBean> equipmentImages) {
        this.equipmentImages = equipmentImages;
    }
}
