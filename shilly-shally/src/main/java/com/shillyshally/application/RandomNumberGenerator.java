package com.shillyshally.application;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNumberGenerator {

    public static final int START_NUMBER = 1;
    private static final Random random = new Random();

    public Long create(Long bound) {
        return random.nextLong(START_NUMBER, bound + 1);
    }
}
