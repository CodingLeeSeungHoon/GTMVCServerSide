package com.example.gtmvcserverside.member.repository;

import com.example.gtmvcserverside.member.domain.GTAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GTAccountInfoRepository extends JpaRepository<GTAccountInfo, Long> {
    boolean existsByAccountID(String accountID);
    Optional<GTAccountInfo> findByAccountID(String accountID);
}
