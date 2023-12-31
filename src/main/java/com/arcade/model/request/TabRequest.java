package com.arcade.model.request;

import io.micrometer.common.util.StringUtils;
import lombok.Data;
import lombok.NonNull;

@Data
public class TabRequest {

    private String name;
    private String phone;
    private Long externalId;

    public TabRequest(@NonNull String name, String phone, @NonNull Long externalId) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("At least of the mandatory parameters is wrong");
        } else {
            this.name = name.trim();
            this.phone = phone.trim();
            this.externalId = externalId;
        }
    }
}
