package com.arcade.exception;

public class OperationNotReadyForCloseoutException extends RuntimeException {

    public OperationNotReadyForCloseoutException() {
        super("FAILED TO CLOSEOUT: There are open tabs");
    }
}
