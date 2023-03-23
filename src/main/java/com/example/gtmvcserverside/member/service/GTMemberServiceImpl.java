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

    /**
     * GT에 가입 비즈니스로직
     * @param joinInRequest 회원가입 요청 DTO
     * @return {@code ResponseEntity<GTJoinInResponse>} 회원가입 응답
     */
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

        // save new member
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
            // if conflict unique keys
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_MEMBER);
        }

    }

    /**
     * 이미 존재하는 ID 인지 확인하는 메소드입니다.
     * @param requestID ID
     * @return boolean 이미 존재하는 ID 여부
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean isAlreadyExistID(String requestID) {
        return accountInfoRepository.existsByAccountID(requestID);
    }

    /**
     * 이미 가입된 회원인지 확인하는 메소드입니다.
     * @param joinInRequest 회원가입 요청 DTO
     * @return boolean 이미 가입된 회원 여부
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean isAlreadyExistMember(GTJoinInRequest joinInRequest) {
        String[] phoneNumberParts = joinInRequest.getPhoneNumber().split("-");
        return memberInfoRepository.existsByNameAndTel1AndTel2AndTel3AndBirthOfDate(
                joinInRequest.getName(),
                phoneNumberParts[0],
                phoneNumberParts[1],
                phoneNumberParts[2],
                joinInRequest.getDateOfBirth());
    }

}
