package com.shillyshally.coredomain.restaurant.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.coredomain.restaurant.Restaurant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 랜덤으로_모든_카테고리의_식당을_조회할_수_있다() {
        Category 한식 = categoryRepository.save(new Category("한식"));
        Category 중식 = categoryRepository.save(new Category("중식"));
        saveRestaurant("알촌1", 한식);
        saveRestaurant("알촌2", 중식);
        saveRestaurant("알촌3", 한식);
        saveRestaurant("알촌4", 중식);

        List<Restaurant> restaurants = restaurantRepository.findRandoms(3);
        Set<Long> categoryIds = restaurants.stream()
                .map(it -> it.getCategoryId())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(restaurants).hasSize(3),
                () -> assertThat(categoryIds).hasSameElementsAs(List.of(한식.getId(), 중식.getId()))
        );
    }

    @Test
    void 랜덤으로_카테고리별_식당을_조회할_수_있다() {
        Category 한식 = categoryRepository.save(new Category("한식"));
        Category 중식 = categoryRepository.save(new Category("중식"));
        saveRestaurant("알촌1", 한식);
        saveRestaurant("알촌2", 중식);
        saveRestaurant("알촌3", 한식);
        saveRestaurant("알촌4", 중식);
        saveRestaurant("알촌5", 한식);
        saveRestaurant("알촌6", 중식);

        List<Restaurant> restaurants = restaurantRepository.findRandomsByCategoryId(한식.getId(), 2);
        Set<Long> categoryIds = restaurants.stream()
                .map(it -> it.getCategoryId())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(restaurants).hasSize(2),
                () -> assertThat(categoryIds).containsExactly(한식.getId())
        );
    }

    private Restaurant saveRestaurant(String name, Category category) {
        return restaurantRepository.save(Restaurant.builder()
                .name(name)
                .categoryId(category.getId())
                .categoryDetail("")
                .phone("")
                .url("")
                .imageUrl("")
                .longitude(0.0)
                .latitude(0.0)
                .build());
    }
}
