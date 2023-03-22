package com.example.gtmvcserverside.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GTJoinInResponse {
    private String description;

    private String createdID;

    private LocalDateTime createdAt;
}
