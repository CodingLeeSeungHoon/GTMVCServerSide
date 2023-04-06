package com.example.gtmvcserverside.member.controller;

import com.example.gtmvcserverside.member.dto.GTAuthLoginRequest;
import com.example.gtmvcserverside.member.service.GTAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GTAuthController {

    private final GTAuthService authService;

    @PostMapping("/login")
    public void loginInGodTong(@RequestBody @Validated GTAuthLoginRequest loginRequest){
        log.info(loginRequest.getAccountID());
        log.info(loginRequest.getAccountPW());
    }
}
