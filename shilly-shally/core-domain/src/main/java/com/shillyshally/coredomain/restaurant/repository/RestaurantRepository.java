package com.shillyshally.coredomain.restaurant.repository;

import com.shillyshally.coredomain.restaurant.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM Restaurant WHERE CATEGORY_ID = :categoryId ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Restaurant> findRandomsByCategoryId(long categoryId, long size);

    @Query(value = "SELECT * FROM Restaurant ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Restaurant> findRandoms(long size);
}
