package com.shillyshally.coredomain.restaurant.repository;

import com.shillyshally.coredomain.restaurant.Restaurant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM Restaurant WHERE CATEGORY = :category ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Restaurant> findAllByCategory(String category, long size);

    @Query(value = "SELECT * FROM Restaurant ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Restaurant> findAll(long size);
}
