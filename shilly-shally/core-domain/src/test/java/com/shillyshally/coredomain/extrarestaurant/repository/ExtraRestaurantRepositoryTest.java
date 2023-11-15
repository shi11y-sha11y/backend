package com.shillyshally.coredomain.extrarestaurant.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.shillyshally.coredomain.extrarestaurant.ExtraRestaurant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;

@DataJpaTest
class ExtraRestaurantRepositoryTest {

    @Autowired
    private ExtraRestaurantRepository extraRestaurantRepository;

    @Test
    void 추가_요청된_식당들을_최신순으로_조회할_수_있다() {
        extraRestaurantRepository.save(new ExtraRestaurant("알촌1"));
        extraRestaurantRepository.save(new ExtraRestaurant("알촌2"));
        ExtraRestaurant 알촌3 = extraRestaurantRepository.save(new ExtraRestaurant("알촌3"));
        ExtraRestaurant 알촌4 = extraRestaurantRepository.save(new ExtraRestaurant("알촌4"));
        ExtraRestaurant 알촌5 = extraRestaurantRepository.save(new ExtraRestaurant("알촌5"));

        Slice<ExtraRestaurant> extraRestaurants = extraRestaurantRepository.findAllByOrderByIdDesc(
                PageRequest.of(0, 3));

        assertAll(
                () -> assertThat(extraRestaurants).hasSize(3),
                () -> assertThat(extraRestaurants.getContent().get(0)).isEqualTo(알촌5),
                () -> assertThat(extraRestaurants.getContent().get(1)).isEqualTo(알촌4),
                () -> assertThat(extraRestaurants.getContent().get(2)).isEqualTo(알촌3),
                () -> assertThat(extraRestaurants.hasNext()).isTrue()
        );
    }
}
