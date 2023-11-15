package com.shillyshally.externalapi.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import com.shillyshally.coredomain.extrarestaurant.repository.ExtraRestaurantRepository;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import com.shillyshally.externalapi.application.dto.PagingExtraRestaurantsResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class ExtraRestaurantServiceTest {

    @Autowired
    private ExtraRestaurantService extraRestaurantService;

    @Autowired
    private ExtraRestaurantRepository extraRestaurantRepository;

    @Test
    void 식당을_추가_등록_요청할_수_있다() {
        extraRestaurantService.create(new ExtraRestaurantRequest("알촌"));

        List<ExtraRestaurant> extraRestaurants = extraRestaurantRepository.findAll();

        assertAll(
                () -> assertThat(extraRestaurants).hasSize(1),
                () -> assertThat(extraRestaurants.get(0).getName()).isEqualTo("알촌")
        );
    }

    @Test
    void 페이지별로_식당을_최신순으로_조회할_수_있다() {
        ExtraRestaurant 알촌1 = extraRestaurantRepository.save(new ExtraRestaurant("알촌1"));
        ExtraRestaurant 알촌2 = extraRestaurantRepository.save(new ExtraRestaurant("알촌2"));
        extraRestaurantRepository.save(new ExtraRestaurant("알촌3"));
        extraRestaurantRepository.save(new ExtraRestaurant("알촌4"));
        extraRestaurantRepository.save(new ExtraRestaurant("알촌5"));

        PagingExtraRestaurantsResponse response = extraRestaurantService.getAll(PageRequest.of(1, 3));

        assertAll(
                () -> assertThat(response.extraRestaurants()).hasSize(2),
                () -> assertThat(response.extraRestaurants().get(0).id()).isEqualTo(알촌2.getId()),
                () -> assertThat(response.extraRestaurants().get(0).name()).isEqualTo(알촌2.getName()),
                () -> assertThat(response.extraRestaurants().get(0).isRegistered()).isEqualTo(알촌2.isRegistered()),
                () -> assertThat(response.extraRestaurants().get(1).id()).isEqualTo(알촌1.getId()),
                () -> assertThat(response.extraRestaurants().get(1).name()).isEqualTo(알촌1.getName()),
                () -> assertThat(response.extraRestaurants().get(1).isRegistered()).isEqualTo(알촌1.isRegistered()),
                () -> assertThat(response.hasNext()).isFalse()
        );
    }
}
