package com.shillyshally.internalapi.ui;

import com.shillyshally.internalapi.application.ExtraRestaurantService;
import com.shillyshally.internalapi.application.dto.RestaurantApprovalRequest;
import com.shillyshally.internalapi.application.dto.PagingExtraRestaurantsResponse;
import com.shillyshally.internalapi.application.dto.DefaultResultResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/extraRestaurants")
public class ExtraRestaurantController {

    private final ExtraRestaurantService extraRestaurantService;

    @GetMapping
    public ResponseEntity<PagingExtraRestaurantsResponse> getAll(Pageable pageable) {
        return ResponseEntity.ok(extraRestaurantService.getAll(pageable));
    }

    @PostMapping
    public ResponseEntity<DefaultResultResponse> approve(List<RestaurantApprovalRequest> restaurantApprovalRequests){
        return ResponseEntity.ok(extraRestaurantService.approve(restaurantApprovalRequests));
    }
}
