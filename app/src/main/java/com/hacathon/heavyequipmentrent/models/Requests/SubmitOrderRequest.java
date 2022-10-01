package com.hacathon.heavyequipmentrent.models.Requests;

public class SubmitOrderRequest {

    Long PropsalId;
    Long UserId;
    Integer RatingValue;

    public SubmitOrderRequest() {
    }

    public Long getPropsalId() {
        return PropsalId;
    }

    public void setPropsalId(Long propsalId) {
        PropsalId = propsalId;
    }

    public Long getUserId() {
        return UserId;
    }

    public void setUserId(Long userId) {
        UserId = userId;
    }

    public Integer getRatingValue() {
        return RatingValue;
    }

    public void setRatingValue(Integer ratingValue) {
        RatingValue = ratingValue;
    }


}
