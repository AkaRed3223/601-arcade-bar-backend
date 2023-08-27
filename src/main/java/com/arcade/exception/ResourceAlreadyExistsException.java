package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;

public class ResourceAlreadyExistsException extends RuntimeException {

    public ResourceAlreadyExistsException(ResourcesEnum resource, ResourcesFieldsEnum field, String value) {
        super(String.format("Resource '%s' with field '%s-%s' already exists", resource, field, value));
    }

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }

    public ResourceAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
