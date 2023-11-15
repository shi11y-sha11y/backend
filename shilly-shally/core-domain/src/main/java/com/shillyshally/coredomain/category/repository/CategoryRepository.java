package com.shillyshally.coredomain.category.repository;

import com.shillyshally.coredomain.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
