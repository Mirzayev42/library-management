package com.library.management.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Map<String, String> errors;
    private LocalDateTime timestamp;

    public ErrorResponse(Map<String, String> errors) {
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }
}
