package com.shillyshally.internalapi.exception.dto;

import com.shillyshally.internalapi.exception.ErrorType;
import org.springframework.http.HttpStatus;

public record ErrorResponse(
        HttpStatus httpStatus,
        int code,
        String message


) {
        public ErrorResponse(ErrorType errorType) {
                this(errorType.getHttpStatus(), errorType.getCode(), errorType.getMessage());
        }

        public ErrorResponse() {
                this(
                        ErrorType.INTERNAL_SERVER_ERROR.getHttpStatus(),
                        ErrorType.INTERNAL_SERVER_ERROR.getCode(),
                        ErrorType.INTERNAL_SERVER_ERROR.getMessage()
                );
        }
}
