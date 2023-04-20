package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTAccountInfo;
import com.example.gtmvcserverside.member.domain.GTAccountUserRoleInfo;
import com.example.gtmvcserverside.member.domain.GTUserRoleInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GTAccountUserRoleInfoRepository extends JpaRepository<GTAccountUserRoleInfo, Long> {

    List<GTAccountUserRoleInfo> findAllByUserRole(GTUserRoleInfo userRoleInfo);

    List<GTAccountUserRoleInfo> findAllByAccountInfo(GTAccountInfo accountInfo);

}
