package com.example.gtmvcserverside.common.enums;

import org.springframework.http.HttpStatus;

/**
 * 공통 에러 코드 Enum 추상화 인터페이스
 */
public interface GTErrorCode {

    String name();
    HttpStatus getHttpStatus();
    String getMessage();

}
