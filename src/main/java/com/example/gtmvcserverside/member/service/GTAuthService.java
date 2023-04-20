package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.dto.GTAuthLoginRequest;
import org.springframework.http.ResponseEntity;

public interface GTAuthService {
    ResponseEntity<?> authenticateUser(GTAuthLoginRequest loginRequest);
}
