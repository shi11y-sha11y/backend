package com.shillyshally.externalapi.application;

import com.shillyshally.coredomain.extrarestaurant.repository.ExtraRestaurantRepository;
import com.shillyshally.externalapi.application.dto.ExtraRestaurantRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ExtraRestaurantService {

    private final ExtraRestaurantRepository extraRestaurantRepository;

    public void create(ExtraRestaurantRequest extraRestaurantRequest) {
        extraRestaurantRepository.save(extraRestaurantRequest.toEntity());
    }
}
