package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;

public class TabAlreadyAssignedException extends RuntimeException {

    public TabAlreadyAssignedException(ResourcesEnum resource, ResourcesFieldsEnum field, String value) {
        super(String.format("Unable to update %s %s-%s. Tab assigned to a different day", resource, field, value));
    }
}
