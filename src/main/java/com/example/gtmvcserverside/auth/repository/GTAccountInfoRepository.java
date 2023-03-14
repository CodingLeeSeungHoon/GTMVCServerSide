package com.example.gtmvcserverside.auth.repository;

import com.example.gtmvcserverside.auth.domain.GTAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GTAccountInfoRepository extends JpaRepository<GTAccountInfo, Long> {
    boolean existsByAccountID(String accountID);
}
