package com.example.gtmvcserverside.member.controller;

import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.dto.GTJoinInResponse;
import com.example.gtmvcserverside.member.service.GTMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Tag(name = "GT Member", description = "GT의 회원 관련 REST API에 대한 명세를 제공합니다. 로그인 및 로그아웃은 Auth 태그를 참조.")
public class GTMemberController {

    private final GTMemberService memberService;

    @PostMapping("/join")
    @Operation(summary = "회원 가입 (joinIn)",
            description = "GT 서비스에 회원가입합니다. GT 에서 기본적으로 제공하는 회원가입 서비스입니다.",
            parameters = {@Parameter(name = "GTJoinInRequest", description = "회원가입 요청 객체")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Join Us!",
                    content = @Content(schema = @Schema(implementation = GTJoinInResponse.class))),
    })
    public ResponseEntity<GTJoinInResponse> joinIn(
            @RequestBody @Validated GTJoinInRequest joinInRequest) {
        return memberService.joinInGodTongService(joinInRequest);
    }


}