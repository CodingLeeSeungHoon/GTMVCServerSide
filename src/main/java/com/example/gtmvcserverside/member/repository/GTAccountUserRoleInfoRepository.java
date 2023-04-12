package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTAccountUserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GTAccountUserRoleInfoRepository extends JpaRepository<GTAccountUserRoleInfo, Long> {
}
