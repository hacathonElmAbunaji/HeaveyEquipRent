package com.hacathon.heavyequipmentrent.database;

import io.realm.Realm;
import io.realm.RealmObject;

public class UserLoginCredBean extends RealmObject {

    String Username;
    String Password;

    public UserLoginCredBean() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
