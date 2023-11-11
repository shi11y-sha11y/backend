package com.shillyshally.externalapi.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.coredomain.restaurant.Restaurant;
import com.shillyshally.coredomain.restaurant.repository.RestaurantRepository;
import com.shillyshally.externalapi.application.dto.RestaurantResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class RestaurantServiceTest {

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 랜덤으로_카테고리별_식당을_조회할_수_있다() {
        Category 한식 = categoryRepository.save(new Category("한식"));
        Category 중식 = categoryRepository.save(new Category("중식"));
        saveRestaurant("알촌1", 한식);
        Restaurant 알촌2 = saveRestaurant("알촌2", 중식);
        saveRestaurant("알촌3", 한식);
        Restaurant 알촌4 = saveRestaurant("알촌4", 중식);
        saveRestaurant("알촌5", 한식);
        Restaurant 알촌6 = saveRestaurant("알촌6", 중식);

        List<RestaurantResponse> response = restaurantService.getRandoms(2L, 중식.getId());
        Set<Long> restaurantIds = response.stream()
                .map(it -> it.id())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(List.of(알촌2.getId(), 알촌4.getId(), 알촌6.getId())).containsAll(restaurantIds)
        );
    }

    @Test
    void 랜덤으로_식당을_조회할_수_있다() {
        Category 한식 = categoryRepository.save(new Category("한식"));
        Category 중식 = categoryRepository.save(new Category("중식"));
        Restaurant 알촌1 = saveRestaurant("알촌1", 한식);
        Restaurant 알촌2 = saveRestaurant("알촌2", 중식);
        Restaurant 알촌3 = saveRestaurant("알촌3", 한식);
        Restaurant 알촌4 = saveRestaurant("알촌4", 중식);
        Restaurant 알촌5 = saveRestaurant("알촌5", 한식);
        Restaurant 알촌6 = saveRestaurant("알촌6", 중식);

        List<RestaurantResponse> response = restaurantService.getRandoms(2L, null);
        Set<Long> restaurantIds = response.stream()
                .map(it -> it.id())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(List.of(알촌1.getId(), 알촌2.getId(), 알촌3.getId(), 알촌4.getId(), 알촌5.getId(),
                        알촌6.getId())).containsAll(restaurantIds)
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
