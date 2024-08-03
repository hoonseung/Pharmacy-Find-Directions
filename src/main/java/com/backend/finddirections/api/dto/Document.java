package com.backend.finddirections.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record Document(

        @JsonProperty("place_name")
        String placeName,

        @JsonProperty("address_name")
        String addressName,

        @JsonProperty("y")
        double latitude,

        @JsonProperty("x")
        double longitude,

        @JsonProperty("distance")
        double distance

) {
}
