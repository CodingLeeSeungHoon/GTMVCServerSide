package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTUserRoleInfo;
import com.example.gtmvcserverside.member.enums.GTUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GTUserRoleInfoRepository extends JpaRepository<GTUserRoleInfo, Long> {
    GTUserRoleInfo findByUserRole(GTUserRole userRole);
}
