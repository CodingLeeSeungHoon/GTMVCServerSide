package com.example.gtmvcserverside.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class GTAuthLoginRequest {

    @NotBlank(message = "로그인 시 Email은 필수 입력사항입니다.")
    private String accountEmail;

    @NotBlank(message = "로그인 시 PW는 필수 입력사항입니다.")
    private String accountPW;
}
