package com.shillyshally.internalapi.application;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import com.shillyshally.coredomain.extrarestaurant.repository.ExtraRestaurantRepository;
import com.shillyshally.coredomain.restaurant.Restaurant;
import com.shillyshally.coredomain.restaurant.repository.RestaurantRepository;
import com.shillyshally.internalapi.application.dto.RestaurantApprovalRequest;
import com.shillyshally.internalapi.application.dto.ExtraRestaurantResponse;
import com.shillyshally.internalapi.application.dto.PagingExtraRestaurantsResponse;
import com.shillyshally.internalapi.application.dto.DefaultResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExtraRestaurantService {

    private final ExtraRestaurantRepository extraRestaurantRepository;
    private final RestaurantRepository restaurantRepository;

    public PagingExtraRestaurantsResponse getAll(Pageable pageable) {
        Slice<ExtraRestaurant> extraRestaurants = extraRestaurantRepository.findAllApprovalStatusUncheckedOrderByIdDesc(pageable);

        List<ExtraRestaurantResponse> extraRestaurantResponses = extraRestaurants.stream()
                .map(it -> new ExtraRestaurantResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());

        return new PagingExtraRestaurantsResponse(extraRestaurantResponses, extraRestaurants.hasNext());
    }

    @Transactional
    public DefaultResultResponse approve(List<RestaurantApprovalRequest> restaurantApprovalRequests){
        List<Long> extraRestaurantIds = restaurantApprovalRequests.stream()
                .map(RestaurantApprovalRequest::extraRestaurantId)
                .collect(Collectors.toList());

        List<Restaurant> restaurants = restaurantApprovalRequests.stream()
                .map(RestaurantApprovalRequest::toEntity)
                .collect(Collectors.toList());

        extraRestaurantRepository.updateStatusApprovedByIds(extraRestaurantIds);
        restaurantRepository.saveAll(restaurants);

        return DefaultResultResponse.ofSuccess();
    }
}
