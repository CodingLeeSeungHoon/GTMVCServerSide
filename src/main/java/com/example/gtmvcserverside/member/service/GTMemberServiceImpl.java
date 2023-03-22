package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.auth.repository.GTAccountInfoRepository;
import com.example.gtmvcserverside.common.enums.GTMemberErrorCode;
import com.example.gtmvcserverside.common.exception.GTApiException;
import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.dto.GTJoinInResponse;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class GTMemberServiceImpl implements GTMemberService {

    private final GTMemberInfoRepository memberInfoRepository;

    private final GTAccountInfoRepository accountInfoRepository;

    private final EntityManager em;

    @Transactional
    public ResponseEntity<GTJoinInResponse> joinInGodTongService(final GTJoinInRequest joinInRequest) {

        // check already exist ID & Member
        String requestID = joinInRequest.getAccountID();
        if (isAlreadyExistID(requestID)) {
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_ID);
        }

        if (isAlreadyExistMember(joinInRequest)) {
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_MEMBER);
        }

        try{
            GTMemberInfo createdMemberInfo = memberInfoRepository.save(GTMemberInfo.fromJoinInDTO(joinInRequest));
            return ResponseEntity.ok()
                    .body(GTJoinInResponse.builder()
                            .description(String.format("%s님의 아이디 '%s'가 성공적으로 생성되었습니다.",
                                    createdMemberInfo.getName(),
                                    createdMemberInfo.getAccountInfo().getAccountID()))
                            .createdID(createdMemberInfo.getAccountInfo().getAccountID())
                            .createdAt(createdMemberInfo.getCreatedAt())
                            .build());
        } catch (DataIntegrityViolationException ex){
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_MEMBER);
        }

    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean isAlreadyExistID(String requestID) {
        return accountInfoRepository.existsByAccountID(requestID);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED,
            propagation = Propagation.REQUIRED,
            readOnly = true)
    public boolean isAlreadyExistMember(GTJoinInRequest joinInRequest) {
        return memberInfoRepository.existGTMemberInfoByNameAndFormatPhoneNumberAndBirthOfDate(
                joinInRequest.getName(),
                joinInRequest.getPhoneNumber(),
                joinInRequest.getDateOfBirth());
    }

}
