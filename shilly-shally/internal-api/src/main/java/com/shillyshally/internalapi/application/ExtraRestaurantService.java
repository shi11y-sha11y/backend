package com.shillyshally.internalapi.application;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import com.shillyshally.coredomain.extrarestaurant.repository.ExtraRestaurantRepository;
import com.shillyshally.internalapi.application.domain.ExtraRestaurantResponse;
import com.shillyshally.internalapi.application.domain.PagingExtraRestaurantsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExtraRestaurantService {

    private final ExtraRestaurantRepository extraRestaurantRepository;

    public PagingExtraRestaurantsResponse getAll(Pageable pageable) {
        Slice<ExtraRestaurant> extraRestaurants = extraRestaurantRepository.findAllApprovalStatusUncheckedOrderByIdDesc(pageable);

        List<ExtraRestaurantResponse> extraRestaurantResponses = extraRestaurants.stream()
                .map(it -> new ExtraRestaurantResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());

        return new PagingExtraRestaurantsResponse(extraRestaurantResponses, extraRestaurants.hasNext());
    }
}
