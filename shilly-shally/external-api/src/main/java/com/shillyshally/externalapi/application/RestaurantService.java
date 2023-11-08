package com.shillyshally.externalapi.application;

import com.shillyshally.coredomain.restaurant.Restaurant;
import com.shillyshally.coredomain.restaurant.repository.RestaurantRepository;
import com.shillyshally.externalapi.application.dto.RestaurantResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantResponse> getRandoms(Long size, Long categoryId) {
        if (categoryId == null) {
            List<Restaurant> restaurants = restaurantRepository.findRandoms(size);
            return getRestaurantsResponse(restaurants);
        }

        List<Restaurant> restaurants = restaurantRepository.findRandomsByCategoryId(categoryId, size);
        return getRestaurantsResponse(restaurants);
    }

    private List<RestaurantResponse> getRestaurantsResponse(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(it -> new RestaurantResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }
}
