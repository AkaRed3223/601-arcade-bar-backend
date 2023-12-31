package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(ResourcesEnum resource, Long id) {
        super(String.format("%s not found with id: %d", resource, id));
    }

    public ResourceNotFoundException(ResourcesEnum resource, String name) {
        super(String.format("%s not found with name: %s", resource, name));
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
