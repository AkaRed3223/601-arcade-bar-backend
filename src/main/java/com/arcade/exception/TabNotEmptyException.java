package com.arcade.exception;

public class TabNotEmptyException extends RuntimeException {

    public TabNotEmptyException() {
        super("Unable to delete - Tab is not empty" );
    }
}
