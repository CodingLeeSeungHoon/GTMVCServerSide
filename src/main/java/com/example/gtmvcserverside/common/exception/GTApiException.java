package com.example.gtmvcserverside.common.exception;

import com.example.gtmvcserverside.common.enums.GTErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GTApiException extends RuntimeException {

    private final GTErrorCode errorCode;

}
