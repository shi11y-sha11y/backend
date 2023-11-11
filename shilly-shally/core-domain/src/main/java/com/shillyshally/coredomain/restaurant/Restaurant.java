package com.shillyshally.coredomain.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long categoryId;

    private String informationUrl;

    private String imageUrl;

    private Double longitude;

    private Double latitude;

    public Restaurant(String name, Long categoryId, String informationUrl,
                      String imageUrl, Double longitude, Double latitude) {
        this(null, name, categoryId, informationUrl, imageUrl, latitude, longitude);
    }
}
