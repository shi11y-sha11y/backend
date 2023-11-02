package com.shillyshally.domain.dao;

import com.shillyshally.domain.Category;
import com.shillyshally.domain.Restaurant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class RestaurantDao {

    private static Long id = 1L;
    private static final Map<Long, Restaurant> restaurants = new HashMap<>();

    static {
        initiallizeRestaurants();
    }

    private static void initiallizeRestaurants() {
        restaurants.put(
                id, Restaurant
                        .builder()
                        .id(id++)
                        .name("알촌")
                        .category(Category.KOREAN)
                        .categoryDetail("")
                        .phone("")
                        .url("")
                        .longitude(126.838432390668)
                        .latitude(37.2995266698506)
                        .build()
        );
        restaurants.put(
                id, Restaurant
                        .builder()
                        .id(id++)
                        .name("밀플랜비")
                        .category(Category.KOREAN)
                        .categoryDetail("")
                        .phone("")
                        .url("")
                        .longitude(126.838432390668)
                        .latitude(37.2995266698506)
                        .build()
        );
        restaurants.put(
                id, Restaurant
                        .builder()
                        .id(id++)
                        .name("도스마스")
                        .category(Category.KOREAN)
                        .categoryDetail("")
                        .phone("")
                        .url("")
                        .longitude(126.838432390668)
                        .latitude(37.2995266698506)
                        .build()
        );
    }

    public Long count() {
        return Long.valueOf(restaurants.size());
    }

    public Restaurant findById(Long id) {
        return restaurants.get(id);
    }
}
