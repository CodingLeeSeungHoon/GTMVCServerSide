package com.example.gtmvcserverside.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

/**
  {@code ResponseEntity}의 {@code body}로써 사용될 클래스입니다.<br>
  {@code code}는 {@code GTErrorCode}의 name 으로 식별합니다.<br>
  {@code message}는 {@code GTErrorCode}가 가지고 있는 메시지를 삽입하거나, {@code Exception}이 소지하고 있는 Message 를 삽입합니다.<br>
 {@code errors}는 validation 과정 중, {@code MethodArgumentNotValidException}가 발생한 경우, 필드 에러를 불러와 저장합니다.<br>
 {@code errors}가 비어있는 경우, Json 형태로 나타내지 않습니다.<br>
 */
@Getter
@Builder
@RequiredArgsConstructor
public class GTErrorResponse {
    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError {

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError fieldError) {
            return ValidationError.builder()
                    .field(fieldError.getField())
                    .message(fieldError.getDefaultMessage())
                    .build();
        }
    }
}
