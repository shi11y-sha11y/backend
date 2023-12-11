package com.shillyshally.internalapi.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorType {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,-10000,"500 Internal Server Error");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
