package com.hacathon.heavyequipmentrent.models.Requests;

public class GetMyRfpsRequest {

    Long UserId;

    public GetMyRfpsRequest() {
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }
}
