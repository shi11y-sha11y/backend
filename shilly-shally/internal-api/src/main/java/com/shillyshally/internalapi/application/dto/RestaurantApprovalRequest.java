package com.shillyshally.internalapi.application.dto;

import com.shillyshally.coredomain.restaurant.Restaurant;

public record RestaurantApprovalRequest(
        long extraRestaurantId,
        String name,
        long categoryId,
        String informationUrl,
        String imageUrl,
        Double longitude,
        Double latitude
) {
    public Restaurant toEntity() {
        return new Restaurant(null,name,categoryId,informationUrl,imageUrl,longitude,latitude);
    }
}
