package com.arcade.model.request;

import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.NonNull;

@Data
public class PaymentRequest {

    private Double value;
    private String name;
    private String details;

    public PaymentRequest(@NonNull Double value, @NonNull String name, String details) {
        if (StringUtils.isEmpty(name) || value == 0.0) {
            throw new IllegalArgumentException("Value and Name are mandatory parameters");
        } else {
            this.name = name.trim();
            this.value = value;
            this.details = details;
        }
    }
}
