package com.arcade.exception;

public class FailedToPayException extends RuntimeException {

    public FailedToPayException(Long id, Double value) {
        super(String.format("Unable to make payment for ID-%s of value %s", id, value));
    }
}
