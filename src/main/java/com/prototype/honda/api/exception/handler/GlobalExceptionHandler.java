package com.prototype.honda.api.exception.handler;

import com.prototype.honda.api.exception.dto.ErrorResponse;
import com.prototype.honda.api.exception.exceptions.BusinessException;
import com.prototype.honda.api.exception.exceptions.InternalServerError;
import com.prototype.honda.api.exception.exceptions.NotAuthorizedException;
import com.prototype.honda.api.exception.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusiness(
            BusinessException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.unprocessableContent()
                .body(buildError(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorized(
            NotAuthorizedException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(buildError(
                        HttpStatus.UNAUTHORIZED,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            NotFoundException ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(buildError(
                        HttpStatus.NOT_FOUND,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        String message = Objects.requireNonNull(ex.getBindingResult()
                        .getFieldError())
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                .body(buildError(
                        HttpStatus.BAD_REQUEST,
                        message,
                        request
                ));
    }

    @ExceptionHandler(InternalServerError.class)
    public ResponseEntity<ErrorResponse> handleInternalServerError(
            InternalServerError ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage(),
                        request
                ));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(
            Exception ex,
            HttpServletRequest request
    ) {

        return ResponseEntity.internalServerError()
                .body(buildError(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        ex.getMessage(),
                        request
                ));
    }

    private ErrorResponse buildError(
            HttpStatus status,
            String message,
            HttpServletRequest request
    ) {

        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .build();
    }
}
