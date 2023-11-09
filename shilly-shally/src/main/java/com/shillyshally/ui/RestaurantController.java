package com.shillyshally.ui;

import com.shillyshally.application.RestaurantService;
import com.shillyshally.application.dto.RestaurantResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/api/v1/restaurants/random")
    public ResponseEntity<List<RestaurantResponse>> getRandom(
            @RequestParam Long size,
            @RequestParam(required = false, defaultValue = "all") String category) {
        return ResponseEntity.ok(restaurantService.getRandom(size, category));
    }
}
