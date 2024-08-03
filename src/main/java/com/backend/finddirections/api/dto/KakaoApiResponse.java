package com.backend.finddirections.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

// record 사용 시 objectMapper.writeValueAsString(expectedResponse) 직렬화 안됨 그래서 클래스로 변경
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoApiResponse {

    private Meta meta;
    private List<Document> documents;


}
