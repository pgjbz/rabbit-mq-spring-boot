package com.pgjbz.subscription.configuration;

import com.pgjbz.subscription.api.http.data.response.FieldMessage;
import com.pgjbz.subscription.api.http.data.response.StandardError;
import com.pgjbz.subscription.api.http.data.response.ValidationError;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Log4j2
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @Value("${springdoc.swagger-ui.path}")
    private String documentationPath;

    @Hidden
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> notSupported(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        var errorMessage = "Not allowed";
        var error = getStandardError(httpStatus, ex.getMessage(), request, errorMessage);
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> badRequest(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var errorMessage = "Bad request";
        var validationError = new ValidationError(
                ex.getMessage(),
                errorMessage,
                request.getRequestURI(),
                Instant.now(),
                documentationPath,
                httpStatus.value());

        for (FieldError x : ex.getBindingResult().getFieldErrors())
            validationError.getErrors().add(new FieldMessage(x.getField(), x.getDefaultMessage()));

        return ResponseEntity.status(httpStatus).body(validationError);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<StandardError> badRequest(HttpMessageNotReadableException ex, HttpServletRequest request) {
        var httpStatus = HttpStatus.BAD_REQUEST;
        var errorMessage = "Bad request, invalid field type";
        var error = getStandardError(httpStatus, ex.getMessage(), request, errorMessage);
        return ResponseEntity.status(httpStatus).body(error);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<StandardError> defaultHandler(Exception ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var errorMessage = "Internal server error";
        var error = getStandardError(httpStatus, ex.getMessage(), request, errorMessage);
        return ResponseEntity.status(httpStatus).body(error);
    }

    private StandardError getStandardError(HttpStatus httpStatus, String errorMessage, HttpServletRequest request, String error){
        return new StandardError(
                errorMessage,
                error,
                request.getRequestURI(),
                Instant.now(),
                documentationPath,
                httpStatus.value());
    }

}
