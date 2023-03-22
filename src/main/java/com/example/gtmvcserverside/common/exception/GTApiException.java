package com.example.gtmvcserverside.common.exception;

import com.example.gtmvcserverside.common.enums.GTErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * {@code GTApiException}은, GT 개발 중 개발자가 throw 할 수 있는 예외로 API 개발 도중 발생하는 모든 예외에 대해 공통적으로 사용합니다. <br>
 * 어떤 예외인지에 대한 구분은, {@code GTErrorCode}를 상속받은 Enum 객체로 판단합니다.<br>
 */
@Getter
@RequiredArgsConstructor
public class GTApiException extends RuntimeException {

    private final GTErrorCode errorCode;

}
