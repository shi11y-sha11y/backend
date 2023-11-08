package com.shillyshally.coredomain.restaurant;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    ASIAN("아시안"),
    SNACK_BAR("분식"),
    MEAT("고기"),
    SANDWICH_SALAD("샌드위치/샐러드"),
    CAFE("카페"),
    BAR("술집");

    private final String label;

    public static Category from(String label) {

        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 카테고리가 없습니다."));
    }
}
