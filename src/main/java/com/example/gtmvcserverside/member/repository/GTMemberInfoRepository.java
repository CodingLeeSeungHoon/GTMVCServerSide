package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTAccountInfo;
import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GTMemberInfoRepository extends JpaRepository<GTMemberInfo, Long> {

    Optional<GTMemberInfo> findGTMemberInfoByAccountInfo(GTAccountInfo accountInfo);

    // boolean existGTMemberInfoByNameAndFormatPhoneNumberAndBirthOfDate(String name, String formatPhoneNumber, LocalDate birthOfDate);

    boolean existsByNameAndTel1AndTel2AndTel3AndBirthOfDate(String name, String tel1, String tel2, String tel3, LocalDate birthOfDate);
}
