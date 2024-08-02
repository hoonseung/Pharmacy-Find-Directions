package com.backend.finddirections.exhandler;

import com.backend.finddirections.api.exception.KakaoClientApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExternalApiExceptionHandler {


    @ExceptionHandler(KakaoClientApiException.class)
    public ResponseEntity<ErrorRes> KakaoClientApiExceptionHandle(final KakaoClientApiException e) {
        return ResponseEntity.ok(new ErrorRes(e.getHttpStatusCode(), e.getMessage()));
    }


}
