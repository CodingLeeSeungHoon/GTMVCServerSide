package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.dto.GTJoinInResponse;
import org.springframework.http.ResponseEntity;

public interface GTMemberService {
    ResponseEntity<GTJoinInResponse> joinInGodTongService(final GTJoinInRequest joinInRequest);
}
