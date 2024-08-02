package com.backend.finddirections.api.dto;

import java.util.List;

public record KakaoApiResponse(

        Meta meta,
        List<Document> documents

) {
}
