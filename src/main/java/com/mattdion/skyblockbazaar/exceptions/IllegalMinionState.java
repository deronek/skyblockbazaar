package com.mattdion.skyblockbazaar.exceptions;

public class IllegalMinionState extends IllegalStateException {
    public IllegalMinionState() {
    }

    public IllegalMinionState(String s) {
        super(s);
    }

    public IllegalMinionState(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalMinionState(Throwable cause) {
        super(cause);
    }
}
