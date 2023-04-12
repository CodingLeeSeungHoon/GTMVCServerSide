package com.example.gtmvcserverside.member.dto;

import com.example.gtmvcserverside.member.enums.GTGender;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Schema(description = "GT 회원가입에 필요한 요청 DTO")
public class GTJoinInRequest {

    @Email(message = "입력한 이메일의 형식이 옳지 않습니다.")
    @Schema(description = "이메일", example = "aaa@example.com")
    private String email;

    @NotBlank(message = "PW는 필수 입력사항입니다.")
    @Schema(description = "계정 비밀번호", example = "Aabc1234!")
    private String accountPW;

    @NotBlank(message = "이름은 필수 입력사항입니다.")
    @Schema(description = "본인 이름", example = "홍길동")
    private String name;

    @Schema(description = "닉네임", nullable = true, example = "갓통팬")
    private String nickName;

    @NotNull
    @Schema(description = "생년월일", example = "1998-11-11")
    private LocalDate dateOfBirth;

    @NotNull
    @Schema(description = "성별", example = "1")
    @Enumerated(EnumType.ORDINAL)
    private GTGender gender;

    @NotNull
    @Schema(description = "이름 공개 여부", example = "true")
    private boolean showName;

    @NotNull(message = "핸드폰 번호를 입력해주세요.")
    @Pattern(regexp = "^01(?:0|1|[6-9])\\d{3,4}\\d{4}$", message = "입력한 핸드폰 번호의 형식이 유효하지 않습니다.")
    @Schema(description = "핸드폰 번호", example = "010-0000-0000")
    private String phoneNumber;
}
