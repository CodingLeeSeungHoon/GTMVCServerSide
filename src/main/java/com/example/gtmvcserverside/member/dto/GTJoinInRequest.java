package com.example.gtmvcserverside.member.dto;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
public class GTJoinInRequest {

    @NotBlank(message = "ID는 필수 입력사항입니다.")
    private String accountID;

    @NotBlank(message = "PW는 필수 입력사항입니다.")
    private String accountPW;

    @NotBlank(message = "이름은 필수 입력사항입니다.")
    private String name;

    private String nickName;

    @NotNull
    private LocalDate dateOfBirth;

    @Email(message = "입력한 이메일의 형식이 옳지 않습니다.")
    private String email;

    @NotNull
    private boolean showName;

    @NotNull(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])\\d{3,4}\\d{4}$", message = "입력한 핸드폰 번호의 형식이 유효하지 않습니다.")
    private String phoneNumber;
}
