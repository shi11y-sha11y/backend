package com.shillyshally.externalapi.ui;

import com.shillyshally.externalapi.application.RestaurantService;
import com.shillyshally.externalapi.application.dto.RestaurantResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/random")
    public ResponseEntity<List<RestaurantResponse>> getRandoms(
            @RequestParam Long size,
            @RequestParam(required = false) Long categoryId) {
        return ResponseEntity.ok(restaurantService.getRandoms(size, categoryId));
    }
}
