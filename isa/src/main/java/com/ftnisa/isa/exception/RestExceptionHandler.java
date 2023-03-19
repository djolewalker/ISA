package com.ftnisa.isa.exception;

import com.ftnisa.isa.dto.error.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Not in this universe!");
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ApiError> handleDenied(Exception ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.FORBIDDEN, "Not in this universe!");
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    public ResponseEntity<ApiError> handleClient(HttpClientErrorException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatusCode(), ex.getLocalizedMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ HttpServerErrorException.class })
    public ResponseEntity<ApiError> handleServer(HttpServerErrorException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatusCode(), ex.getLocalizedMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ HandledException.class })
    public ResponseEntity<ApiError> handleAllHandled(HandledException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatus(), ex.getLocalizedMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }

    @ExceptionHandler({ ResourceConflictException.class })
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(
            ResourceConflictException ex,
            WebRequest request) {
        var apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
        return new ResponseEntity<ApiError>(apiError, apiError.getStatus());
    }
}
