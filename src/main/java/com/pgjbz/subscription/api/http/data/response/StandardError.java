package com.pgjbz.subscription.api.http.data.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.lang.NonNull;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Objects;

@Getter
@Schema(name = "Default error response")
public class StandardError {

    @Schema(name = "message", defaultValue = "Invalid field value")
    private final String message;
    @Schema(name = "documentation", defaultValue = "/documentation")
    private final String documentation;
    @Schema(name = "documentation", defaultValue = "/subscription")
    private final String path;
    private final Instant timestamp;
    @Schema(name = "status", defaultValue = "404")
    private final Integer status;
    @Schema(name = "error", defaultValue = "Bad request")
    private final String error;

    public StandardError(@NonNull String message,
                         @NonNull String error,
                         @NonNull String path,
                         @NonNull Instant timestamp,
                         @NonNull String documentation,
                         @NonNull Integer status) {
        this.message = Objects.requireNonNull(message);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        this.documentation = request.getRequestURL().toString().replace(request.getRequestURI(), "") + documentation;
        this.path =  Objects.requireNonNull(path);
        this.timestamp =  Objects.requireNonNull(timestamp);
        this.status = Objects.requireNonNull(status);
        this.error = Objects.requireNonNull(error);
    }

}
