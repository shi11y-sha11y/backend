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
    WESTERN("양식");

    private final String label;

    public static Category from(String label) {

        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(label))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하는 카테고리가 없습니다."));
    }
}
