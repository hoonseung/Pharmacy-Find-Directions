package com.backend.finddirections.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Document(
        @JsonProperty("address_name")
        String addressName,

        @JsonProperty("y")
        double latitude,

        @JsonProperty("x")
        double longitude

) {
}
