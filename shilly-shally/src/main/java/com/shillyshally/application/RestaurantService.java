package com.shillyshally.application;

import com.shillyshally.application.dto.RestaurantResponse;
import com.shillyshally.domain.Restaurant;
import com.shillyshally.domain.dao.RestaurantDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RestaurantService {

    private final RestaurantDao restaurantDao;
    private final RandomNumberGenerator randomNumberGenerator;

    public RestaurantResponse getOne() {
        Long counts = restaurantDao.count();
        Long randomNumber = randomNumberGenerator.create(counts);
        Restaurant restaurant = restaurantDao.findById(randomNumber);
        return new RestaurantResponse(restaurant.getName());
    }
}
