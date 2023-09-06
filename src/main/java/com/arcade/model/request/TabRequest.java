package com.arcade.model.request;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.beans.ConstructorProperties;
import java.util.List;

@Data
public class TabRequest {

    private String name;
    private Long externalId;

    @ConstructorProperties({"firstName", "lastName"})
    TabRequest(@NonNull String name, @NonNull Long externalId) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("name");
        } else {
            this.name = name;
            this.externalId = externalId;
        }
    }
}
