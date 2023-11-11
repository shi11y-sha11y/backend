package com.shillyshally.externalapi.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.externalapi.application.dto.CategoryResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

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

        List<CategoryResponse> response = categoryService.getAll();
        Set<String> categoryNames = response.stream()
                .map(it -> it.name())
                .collect(Collectors.toSet());

        assertAll(
                () -> assertThat(response).hasSize(3),
                () -> assertThat(categoryNames).containsExactlyInAnyOrder(firstName, secondName, thirdName)
        );
    }
}
