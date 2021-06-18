package com.mattdion.skyblockbazaar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
public class HypixelAPIConnectionException extends RuntimeException {
    public HypixelAPIConnectionException() {
    }

    public HypixelAPIConnectionException(String message) {
        super(message);
    }

    public HypixelAPIConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public HypixelAPIConnectionException(Throwable cause) {
        super(cause);
    }
}
