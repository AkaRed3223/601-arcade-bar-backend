package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;

public class FailedToInactivateResourceException extends RuntimeException {
    public FailedToInactivateResourceException(ResourcesEnum resource, ResourcesFieldsEnum field, String value) {
        super(String.format("Failed to inactivate %s %s-%s.", resource, field, value));
    }
}
