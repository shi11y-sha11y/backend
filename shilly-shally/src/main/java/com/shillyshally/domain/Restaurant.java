package com.shillyshally.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Restaurant {

    @Id
    private Long id;

    private String name;

    private Category category;

    private String categoryDetail;

    private String phone;

    private String url;

    private Double longitude;

    private Double latitude;
}
