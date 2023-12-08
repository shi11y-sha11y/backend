package com.shillyshally.externalapi.application.dto;

import com.shillyshally.coredomain.extrarestaurant.ApprovalStatus;

public record ExtraRestaurantResponse(
        long id,
        String name,
        ApprovalStatus approvalStatus
) {

}
