package com.shillyshally.externalapi.ui;

import com.shillyshally.externalapi.application.ExtraRestaurantService;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/extraRestaurants")
public class ExtraRestaurantController {

    private final ExtraRestaurantService extraRestaurantService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody ExtraRestaurantRequest extraRestaurantRequest) {
        extraRestaurantService.create(extraRestaurantRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
