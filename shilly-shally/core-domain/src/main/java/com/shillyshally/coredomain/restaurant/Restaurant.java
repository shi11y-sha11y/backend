package com.shillyshally.coredomain.restaurant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String categoryDetail;

    private String phone;

    private String url;

    private String imageUrl;

    private Double longitude;

    private Double latitude;

    public Restaurant(String name, Long categoryId, String categoryDetail, String phone, String url,
                      String imageUrl, Double longitude, Double latitude) {
        this(null, name, categoryId, categoryDetail, phone, url, imageUrl, latitude, longitude);
    }
}
