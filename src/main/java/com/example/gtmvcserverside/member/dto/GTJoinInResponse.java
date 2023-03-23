package com.example.gtmvcserverside.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "GT 회원가입 응답 DTO")
public class GTJoinInResponse {

    @Schema(description = "회원가입 완료 문구", example = "XXX님의 ID 'user1234'가 회원가입이 완료되었습니다.")
    private String description;

    @Schema(description = "생성된 ID", example = "user1234")
    private String createdID;

    @Schema(description = "생성된 시간")
    private LocalDateTime createdAt;
}
