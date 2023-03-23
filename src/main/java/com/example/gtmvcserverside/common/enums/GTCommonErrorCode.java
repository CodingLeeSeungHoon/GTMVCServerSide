package com.example.gtmvcserverside.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 공통 에러 코드 Enum 추상화 인터페이스인 {@code GTErrorCode}를 구현한 Enum 클래스<br>
 * 특정 도메인이 아닌, 공통적으로 나타날 수 있는 {@code Exception}에 대해 에러코드로 정의한 객체들입니다.<br>
 * {@code GTApiException}을 발생시키고, 내부의 ErrorCode를 설정하는 것으로 어떤 Exception인지 규제하는 방식으로 사용됩니다.<br>
 */
@Getter
@RequiredArgsConstructor
public enum GTCommonErrorCode implements GTErrorCode {

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter included"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not exists"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}
