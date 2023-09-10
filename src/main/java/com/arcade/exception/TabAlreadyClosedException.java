package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;

public class TabAlreadyClosedException extends RuntimeException {

    public TabAlreadyClosedException(ResourcesEnum resource, ResourcesFieldsEnum field, String value) {
        super(String.format("Unable to update %s %s-%s. Tab already closed", resource, field, value));
    }
}
