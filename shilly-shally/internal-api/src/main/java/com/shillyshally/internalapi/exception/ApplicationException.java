package com.shillyshally.internalapi.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException{

    private ErrorType errorType;

    public ApplicationException(String message, ErrorType errorType){
        super(message);
        this.errorType = errorType;
    }
}
