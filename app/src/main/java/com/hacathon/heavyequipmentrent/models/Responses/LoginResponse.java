package com.hacathon.heavyequipmentrent.models.Responses;

public class LoginResponse {

    Long responseCode;
    Long userId;
    String token;
    String expires;
    String displayName;

    public LoginResponse() {
    }

    public Long getCode() {
        return responseCode;
    }

    public void setCode(Long code) {
        this.responseCode = code;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}

