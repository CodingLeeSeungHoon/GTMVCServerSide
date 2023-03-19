package com.example.gtmvcserverside.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 공통 에러 코드 Enum 추상화 인터페이스인 {@code GTErrorCode}를 구현한 Enum 클래스<br>
 * 회원 관련 도메인에서 발생 가능한 예외 상황에 대해 에러코드로 정의한 객체들입니다.<br>
 * {@code GTApiException}을 발생시키고, 내부의 ErrorCode를 설정하는 것으로 어떤 Exception인지 규제하는 방식으로 사용됩니다.<br>
 */
@Getter
@RequiredArgsConstructor
public enum GTMemberErrorCode implements GTErrorCode{
    ALREADY_EXIST_MEMBER(HttpStatus.CONFLICT, "Already exist member, try to find your account"),
    ALREADY_EXIST_ID(HttpStatus.CONFLICT, "Already exist id, try to choice another id")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
