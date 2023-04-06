package com.example.gtmvcserverside.common.exception;

import com.example.gtmvcserverside.common.dto.GTErrorResponse;
import com.example.gtmvcserverside.common.enums.GTCommonErrorCode;
import com.example.gtmvcserverside.common.enums.GTErrorCode;
import com.example.gtmvcserverside.common.jsonview.GTJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GTControllerAdvice extends ResponseEntityExceptionHandler {

    /* ExceptionHandler Space */

    /**
     * {@code IllegalArgumentException}에 대해 전역적으로 제어하는 핸들러입니다.<br>
     * 내부적으로 {@code handleExceptionInternal(final GTErrorCode errorCode, String message)}를 호출합니다.<br>
     *
     * @param ex 발생한 예외
     * @return {@code ResponseEntity<GTErrorResponse>}
     */
    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<GTErrorResponse> handleIllegalArgumentException(Exception ex) {
        log.warn(ex.getMessage());
        GTErrorCode errorCode = GTCommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, ex.getMessage());
    }

    /**
     * {@code GTApiException}에 대해 전역적으로 제어하는 핸들러입니다.<br>
     * {@code GTApiException}는, GT 개발 중 개발자가 throw 할 수 있는 예외로, <br>
     * API 개발 도중 발생하는 모든 예외에 대해 공통적으로 사용합니다.<br>
     * 어떤 예외인지에 대한 구분은, {@code GTErrorCode}를 상속받은 Enum 객체로 판단합니다.<br>
     * 내부적으로 {@code handleExceptionInternal(final GTErrorCode errorCode)}를 호출합니다.<br>
     *
     * @param ex 발생한 예외
     * @return {@code ResponseEntity<GTErrorResponse>}
     */
    @ExceptionHandler(value = {GTApiException.class})
    public ResponseEntity<GTErrorResponse> handleExceptionFromAPIMethod(GTApiException ex) {
        GTErrorCode errorCode = ex.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    /**
     * {@code MethodArgumentNotValidException}에 대해 전역적으로 제어하는 핸들러입니다.<br>
     * 내부적으로 {@code handleExceptionInternal(final GTErrorCode errorCode, MethodArgumentNotValidException ex)}를 호출합니다.<br>
     * {@code GTErrorCode}는, {@code GTCommonErrorCode.INVALID_PARAMETER}로 고정합니다.<br>
     *
     * @param ex 발생한 예외
     * @return {@code ResponseEntity<GTErrorResponse>}
     */
    @Override
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Bad Request!",
                    content = @Content(schema = @Schema(implementation = GTErrorResponse.class))),
    })
    @JsonView(GTJsonView.Hidden.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.warn("handleIllegalArgument", ex);
        GTErrorCode errorCode = GTCommonErrorCode.INVALID_PARAMETER;
        return handleExceptionInternal(errorCode, ex);
    }


    /* handleExceptionInternal Method Space */

    private ResponseEntity<GTErrorResponse> handleExceptionInternal(final GTErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponseByErrorCode(errorCode));
    }

    private ResponseEntity<GTErrorResponse> handleExceptionInternal(final GTErrorCode errorCode, String message) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponseByErrorCodeAndMessage(errorCode, message));
    }

    private ResponseEntity<Object> handleExceptionInternal(final GTErrorCode errorCode, MethodArgumentNotValidException ex) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponseByErrorCodeAndException(errorCode, ex));
    }

    /* makeErrorResponse Method Space */

    private GTErrorResponse makeErrorResponseByErrorCode(final GTErrorCode errorCode) {
        return GTErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

    private GTErrorResponse makeErrorResponseByErrorCodeAndMessage(final GTErrorCode errorCode, String message) {
        return GTErrorResponse.builder()
                .code(errorCode.name())
                .message(message)
                .build();
    }

    private GTErrorResponse makeErrorResponseByErrorCodeAndException(final GTErrorCode errorCode, MethodArgumentNotValidException ex) {
        List<GTErrorResponse.ValidationError> validationErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(GTErrorResponse.ValidationError::of)
                .collect(Collectors.toList());

        return GTErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .errors(validationErrors)
                .build();
    }

}