package com.shillyshally.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Place {

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
