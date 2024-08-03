package com.backend.finddirections.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Document(
        @JsonProperty("address_name")
        String addressName,

        @JsonProperty("y")
        double latitude,

        @JsonProperty("x")
        double longitude

) {
}
