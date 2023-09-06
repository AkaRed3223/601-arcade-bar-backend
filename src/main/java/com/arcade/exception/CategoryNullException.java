package com.arcade.exception;

public class CategoryNullException extends RuntimeException {

    public CategoryNullException() {
        super("Category name must not be null");
    }


}
