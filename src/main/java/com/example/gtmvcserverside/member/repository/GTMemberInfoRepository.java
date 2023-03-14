package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GTMemberInfoRepository extends JpaRepository<GTMemberInfo, Long> {
}
