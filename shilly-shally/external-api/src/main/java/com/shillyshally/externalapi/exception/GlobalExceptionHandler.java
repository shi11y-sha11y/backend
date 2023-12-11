package com.shillyshally.externalapi.exception;


import com.shillyshally.externalapi.exception.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> applicationException(RuntimeException e) {
        log.info(String.format("Runtime Exception : %s", e));
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> runtimeException(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        log.error(String.format("UnHandled Exception : %s\n" + "%s:%s:%s", e, stackTrace[0].getClassName(),
                stackTrace[0].getMethodName(), stackTrace[0].getLineNumber()), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse("internal server error"));
    }
}
