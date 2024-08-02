package com.backend.finddirections.api.exception;

import lombok.Getter;

@Getter
public class KakaoClientApiException extends RuntimeException {

    private final int httpStatusCode;

    public KakaoClientApiException(String message, int httpStatus) {
        super(message);
        this.httpStatusCode = httpStatus;
    }
}
