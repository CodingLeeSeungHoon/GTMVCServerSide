package com.example.gtmvcserverside.auth.controller;

import com.example.gtmvcserverside.auth.service.GTAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class GTAuthController {

    private final GTAuthService authService;


}
