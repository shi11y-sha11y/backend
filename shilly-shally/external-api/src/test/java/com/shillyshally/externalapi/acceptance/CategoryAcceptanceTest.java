package com.shillyshally.externalapi.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.externalapi.acceptance.common.AcceptanceTest;
import com.shillyshally.externalapi.application.dto.CategoryResponse;
import io.restassured.RestAssured;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class CategoryAcceptanceTest extends AcceptanceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void 모든_카테고리를_조회할_수_있다() {
        String firstName = "한식";
        String secondName = "중식";
        String thirdName = "일식";
        categoryRepository.save(new Category(firstName));
        categoryRepository.save(new Category(secondName));
        categoryRepository.save(new Category(thirdName));

        List<CategoryResponse> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .get("/api/v1/categories")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .jsonPath()
                .getList(".", CategoryResponse.class);

        Set<String> responseCategoryNames = response.stream()
                .map(it -> it.name())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(response).hasSize(3),
                () -> assertThat(responseCategoryNames).containsExactlyInAnyOrder(firstName, secondName, thirdName)
        );
    }
}
