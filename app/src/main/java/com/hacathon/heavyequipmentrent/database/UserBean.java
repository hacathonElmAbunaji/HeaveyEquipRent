package com.hacathon.heavyequipmentrent.database;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class UserBean extends RealmObject {


    @PrimaryKey
    public long UserID;

    public UserBean() {
    }

    public long getUserID() {
        return UserID;
    }

    public void setUserID(long userID) {
        UserID = userID;
    }


}
