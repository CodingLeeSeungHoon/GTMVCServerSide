package com.example.gtmvcserverside.auth.dto;

import com.example.gtmvcserverside.auth.domain.GTAccountInfo;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
public class GTAuthLoginRequest {

    @NotBlank(message = "로그인 시 ID는 필수 입력사항입니다.")
    private String accountID;

    @NotBlank(message = "로그인 시 PW는 필수 입력사항입니다.")
    private String accountPW;
}
