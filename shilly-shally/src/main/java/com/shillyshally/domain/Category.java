package com.shillyshally.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식");

    private final String label;
}
