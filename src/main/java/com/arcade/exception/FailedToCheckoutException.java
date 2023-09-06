package com.arcade.exception;

import com.arcade.constant.ResourcesEnum;
import com.arcade.constant.ResourcesFieldsEnum;

public class FailedToCheckoutException extends RuntimeException {

    public FailedToCheckoutException(ResourcesEnum resource, ResourcesFieldsEnum field, String value) {
        super(String.format("Unable to checkout %s with %s-%s", resource, field, value));
    }


}
