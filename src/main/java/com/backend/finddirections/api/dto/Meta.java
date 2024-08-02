package com.backend.finddirections.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;


public record Meta(
        @JsonProperty("total_count")
        Integer totalCount) {
}
