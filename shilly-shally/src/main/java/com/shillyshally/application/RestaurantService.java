package com.shillyshally.application;

import com.shillyshally.application.dto.RestaurantResponse;
import com.shillyshally.domain.Restaurant;
import com.shillyshally.domain.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RandomNumberGenerator randomNumberGenerator;

    public RestaurantResponse getOne() {
        long counts = restaurantRepository.count();
        long randomNumber = randomNumberGenerator.create(counts);
        Restaurant restaurant = restaurantRepository.findById(randomNumber)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 음식점입니다."));
        return new RestaurantResponse(restaurant.getName());
    }
}
