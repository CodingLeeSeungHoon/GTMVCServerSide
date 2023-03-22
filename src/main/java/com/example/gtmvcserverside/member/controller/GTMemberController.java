package com.example.gtmvcserverside.member.controller;

import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.dto.GTJoinInResponse;
import com.example.gtmvcserverside.member.service.GTMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GTMemberController {

    private final GTMemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<GTJoinInResponse> joinIn(@RequestBody @Validated GTJoinInRequest joinInRequest){
        return memberService.joinInGodTongService(joinInRequest);
    }

}