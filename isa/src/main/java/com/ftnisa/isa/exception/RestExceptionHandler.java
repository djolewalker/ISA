package com.ftnisa.isa.exception;

import com.ftnisa.isa.dto.error.ApiError;
import com.ftnisa.isa.dto.error.ORSError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.UNAUTHORIZED, "Not in this universe!");
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ApiError> handleDenied(Exception ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.FORBIDDEN, "Not in this universe!");
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ HttpClientErrorException.class })
    public ResponseEntity<ApiError> handleClient(HttpClientErrorException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatusCode(), ex.getLocalizedMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ HttpServerErrorException.class })
    public ResponseEntity<ApiError> handleServer(HttpServerErrorException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatusCode(), ex.getLocalizedMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ HandledException.class })
    public ResponseEntity<ApiError> handleAllHandled(HandledException ex, WebRequest request) {
        var apiError = new ApiError(ex.getStatus(), ex.getLocalizedMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ ResourceConflictException.class })
    public ResponseEntity<ApiError> handleMethodArgumentTypeMismatch(ResourceConflictException ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.CONFLICT, ex.getLocalizedMessage());
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }

    @ExceptionHandler({ ORSException.class })
    public ResponseEntity<ORSError> handleORSException(ORSException ex, WebRequest request) {
        var orsError = new ORSError(HttpStatus.BAD_REQUEST, ex.getOrsError());
        return ResponseEntity.status(orsError.getStatus()).body(orsError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getAllErrors().forEach(err -> errors.add(err.getDefaultMessage()));

        Map<String, List<String>> result = new HashMap<>();
        result.put("errors", errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ TempRouteExpired.class })
    public ResponseEntity<ApiError> handleTempRouteExpired(TempRouteExpired ex, WebRequest request) {
        var apiError = new ApiError(HttpStatus.CONFLICT, "Temporal route expired!");
        return ResponseEntity.status(apiError.getStatus()).body(apiError);
    }
}
