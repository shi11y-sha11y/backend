package com.shillyshally.externalapi.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.externalapi.acceptance.commons.AcceptanceTest;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import com.shillyshally.externalapi.application.dto.PagingExtraRestaurantsResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public class ExtraRestaurantAcceptanceTest extends AcceptanceTest {

    @Test
    public void 유저는_식당을_추가_요청할_수_있다() {
        식당_추가_요청("알촌");
    }

    @Test
    public void 유저는_추가_요청된_식당_리스트들을_조회할_수_있다() {
        식당_추가_요청("알촌1");
        식당_추가_요청("알촌2");
        식당_추가_요청("알촌3");
        식당_추가_요청("알촌4");
        식당_추가_요청("알촌5");

        PagingExtraRestaurantsResponse response = 추가_요청된_식당_조회(0, 3);

        assertAll(
                () -> assertThat(response.extraRestaurants()).hasSize(3),
                () -> assertThat(response.extraRestaurants().get(0).id()).isEqualTo(5),
                () -> assertThat(response.extraRestaurants().get(0).name()).isEqualTo("알촌5"),
                () -> assertThat(response.extraRestaurants().get(0).isRegistered()).isFalse(),
                () -> assertThat(response.hasNext()).isTrue()
        );
    }

    private void 식당_추가_요청(String name) {
        RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(new ExtraRestaurantRequest(name))
                .when()
                .post("/api/v1/extraRestaurants")
                .then().log().all()
                .statusCode(HttpStatus.CREATED.value());
    }

    private PagingExtraRestaurantsResponse 추가_요청된_식당_조회(int page, int size) {
        return RestAssured
                .given().log().all()
                .param("size", size)
                .param("page", page)
                .when()
                .get("/api/v1/extraRestaurants")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(PagingExtraRestaurantsResponse.class);
    }
}
