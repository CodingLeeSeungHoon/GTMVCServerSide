package com.example.gtmvcserverside.member.enums;

import com.example.gtmvcserverside.member.domain.GTUserRoleInfo;
import com.example.gtmvcserverside.member.repository.GTUserRoleInfoRepository;

public enum GTUserRole {
    ROLE_FAN,
    ROLE_STAR,
    ROLE_MANAGER,
    ROLE_ADMIN;

    public GTUserRoleInfo convertToUserRoleInfo(GTUserRoleInfoRepository userRoleInfoRepository){
        return userRoleInfoRepository.findByUserRole(this);
    }
}
