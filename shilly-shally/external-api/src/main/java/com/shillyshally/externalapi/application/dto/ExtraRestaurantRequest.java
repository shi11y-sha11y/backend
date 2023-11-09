package com.shillyshally.externalapi.application.dto;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;

public record ExtraRestaurantRequest(String name) {

    public ExtraRestaurant toEntity() {
        return new ExtraRestaurant(name);
    }
}
