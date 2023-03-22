package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.auth.domain.GTAccountInfo;
import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GTMemberInfoRepository extends JpaRepository<GTMemberInfo, Long> {

    Optional<GTMemberInfo> findGTMemberInfoByAccountInfo(GTAccountInfo accountInfo);

    boolean existGTMemberInfoByNameAndFormatPhoneNumberAndBirthOfDate(String name, String formatPhoneNumber, LocalDate birthOfDate);
}
