package com.shillyshally.internalapi.application.domain;

import java.util.List;

public record PagingExtraRestaurantsResponse(
        List<ExtraRestaurantResponse> extraRestaurants,
        boolean hasNext
) {
}
