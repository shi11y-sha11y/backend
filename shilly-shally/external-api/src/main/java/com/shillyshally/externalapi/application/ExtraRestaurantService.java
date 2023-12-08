package com.shillyshally.externalapi.application;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import com.shillyshally.coredomain.extrarestaurant.repository.ExtraRestaurantRepository;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantResponse;
import com.shillyshally.externalapi.application.dto.PagingExtraRestaurantsResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ExtraRestaurantService {

    private final ExtraRestaurantRepository extraRestaurantRepository;

    @Transactional
    public void create(ExtraRestaurantRequest extraRestaurantRequest) {
        extraRestaurantRepository.save(extraRestaurantRequest.toEntity());
    }

    public PagingExtraRestaurantsResponse getAll(Pageable pageable) {
        Slice<ExtraRestaurant> extraRestaurants = extraRestaurantRepository.findAllByOrderByIdDesc(pageable);

        List<ExtraRestaurantResponse> extraRestaurantResponses = extraRestaurants.stream()
                .map(it -> new ExtraRestaurantResponse(it.getId(), it.getName(), it.getApprovalStatus()))
                .collect(Collectors.toList());

        return new PagingExtraRestaurantsResponse(extraRestaurantResponses, extraRestaurants.hasNext());
    }
}
