package com.pgjbz.subscription.api.http.data.response;

import lombok.Getter;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Getter
public class ValidationError extends StandardError {

    private final List<FieldMessage> errors = new LinkedList<>();


    public ValidationError(String message, String error, String path, Instant timestamp, String documentation, Integer status) {
        super(message, error, path, timestamp, documentation, status);
    }
}
