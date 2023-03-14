package com.example.gtmvcserverside.member.controller;

import com.example.gtmvcserverside.member.service.GTMemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GTMemberController {

    private final GTMemberService memberService;


}
