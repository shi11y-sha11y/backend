package com.shillyshally.ui;

import com.shillyshally.application.RestaurantService;
import com.shillyshally.application.dto.RestaurantResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/api/v1/restaurant")
    public ResponseEntity<RestaurantResponse> getOne() {
        return ResponseEntity.ok(restaurantService.getOne());
    }
}
