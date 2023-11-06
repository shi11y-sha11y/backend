package com.shillyshally.application;

import com.shillyshally.application.dto.RestaurantResponse;
import com.shillyshally.domain.Category;
import com.shillyshally.domain.Restaurant;
import com.shillyshally.domain.repository.RestaurantRepository;
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

    public List<RestaurantResponse> getRandom(Long size, String category) {
        if (category.equals("all")) {
            List<Restaurant> restaurants = restaurantRepository.findAll(size);
            return getRestaurantsResponse(restaurants);
        }
        Category foundCategory = Category.from(category);
        List<Restaurant> restaurants = restaurantRepository.findAllByCategory(foundCategory.name(), size);
        return getRestaurantsResponse(restaurants);
    }

    private List<RestaurantResponse> getRestaurantsResponse(List<Restaurant> restaurants) {
        return restaurants.stream()
                .map(it -> new RestaurantResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }
}
