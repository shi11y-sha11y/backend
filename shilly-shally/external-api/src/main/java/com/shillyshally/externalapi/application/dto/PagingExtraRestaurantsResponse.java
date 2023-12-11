package com.shillyshally.externalapi.application.dto;

import java.util.List;

public record PagingExtraRestaurantsResponse(
        List<ExtraRestaurantResponse> extraRestaurants,
        boolean hasNext
) {

}
