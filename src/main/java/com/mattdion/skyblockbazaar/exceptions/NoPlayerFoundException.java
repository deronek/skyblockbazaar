package com.mattdion.skyblockbazaar.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoPlayerFoundException extends Exception {
    public NoPlayerFoundException() {
    }

    public NoPlayerFoundException(String message) {
        super(message);
    }

    public NoPlayerFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoPlayerFoundException(Throwable cause) {
        super(cause);
    }
}
