package com.shillyshally.externalapi.application;

import com.shillyshally.coredomain.category.Category;
import com.shillyshally.coredomain.category.repository.CategoryRepository;
import com.shillyshally.externalapi.application.dto.CategoryResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();
        return getCategoriesResponse(categories);
    }

    private List<CategoryResponse> getCategoriesResponse(List<Category> categories) {
        return categories.stream()
                .map(it -> new CategoryResponse(it.getId(), it.getName()))
                .collect(Collectors.toList());
    }
}
