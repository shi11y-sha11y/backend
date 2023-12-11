package com.shillyshally.externalapi.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.coredomain.restaurant.Restaurant;
import com.shillyshally.coredomain.restaurant.repository.RestaurantRepository;
import com.shillyshally.externalapi.acceptance.common.AcceptanceTest;
import com.shillyshally.externalapi.application.dto.RestaurantResponse;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class RestaurantAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void 카테고리_상관없이_식당들을_랜덤으로_조회할_수_있다() {
        String firstCategoryName = "한식";
        String secondCategoryName = "중식";
        Category firstCategory = categoryRepository.save(new Category(firstCategoryName));
        Category secondCategory = categoryRepository.save(new Category(secondCategoryName));

        String firstRestaurantName = "알촌1";
        String secondRestaurantName = "알촌2";
        String thirdRestaurantName = "알촌3";

        saveRestaurant(firstRestaurantName, firstCategory);
        saveRestaurant(secondRestaurantName, firstCategory);
        saveRestaurant(thirdRestaurantName, secondCategory);

        List<RestaurantResponse> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("size", 2)
                .when()
                .get("/api/v1/restaurants/random")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", RestaurantResponse.class);

        assertThat(response).hasSize(2);
    }

    @Test
    void 카테고리별로_식당들을_랜덤으로_조회할_수_있다() {
        String firstCategoryName = "한식";
        String secondCategoryName = "중식";
        Category firstCategory = categoryRepository.save(new Category(firstCategoryName));
        Category secondCategory = categoryRepository.save(new Category(secondCategoryName));

        String firstRestaurantName = "알촌1";
        String secondRestaurantName = "알촌2";
        String thirdRestaurantName = "알촌3";

        saveRestaurant(firstRestaurantName, firstCategory);
        saveRestaurant(secondRestaurantName, firstCategory);
        saveRestaurant(thirdRestaurantName, secondCategory);

        List<RestaurantResponse> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("size", 2)
                .param("categoryId", firstCategory.getId())
                .when()
                .get("/api/v1/restaurants/random")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", RestaurantResponse.class);

        Set<Long> responseCategoryIds = response.stream()
                .map(RestaurantResponse::categoryId)
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(response).hasSize(2),
                () -> assertThat(responseCategoryIds).containsExactlyInAnyOrder(firstCategory.getId())
        );
    }

    private Restaurant saveRestaurant(String name, Category category) {
        return restaurantRepository.save(Restaurant.builder()
                .name(name)
                .categoryId(category.getId())
                .informationUrl("")
                .imageUrl("")
                .longitude(0.0)
                .latitude(0.0)
                .build());
    }
}
