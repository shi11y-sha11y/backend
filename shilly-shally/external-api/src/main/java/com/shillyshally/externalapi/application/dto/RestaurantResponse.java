package com.shillyshally.externalapi.application.dto;

import com.shillyshally.coredomain.restaurant.Restaurant;

public record RestaurantResponse(
        long id,
        String name,
        long categoryId,
        String informationUrl,
        String imageUrl,
        double longitude,
        double latitude
) {

    public static RestaurantResponse from(Restaurant restaurant) {
        return new RestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCategoryId(),
                restaurant.getInformationUrl(),
                restaurant.getImageUrl(),
                restaurant.getLongitude(),
                restaurant.getLatitude()
        );
    }
}
