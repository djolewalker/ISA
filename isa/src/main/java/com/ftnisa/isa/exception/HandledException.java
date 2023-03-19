package com.ftnisa.isa.exception;

import org.springframework.http.HttpStatus;

public class HandledException extends RuntimeException {
    private final HttpStatus status;

    public HandledException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}