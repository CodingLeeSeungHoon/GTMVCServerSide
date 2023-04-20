package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.domain.GTAccountUserRoleInfo;
import com.example.gtmvcserverside.member.enums.GTUserRole;
import com.example.gtmvcserverside.member.repository.GTAccountInfoRepository;
import com.example.gtmvcserverside.common.enums.GTMemberErrorCode;
import com.example.gtmvcserverside.common.exception.GTApiException;
import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.dto.GTJoinInResponse;
import com.example.gtmvcserverside.member.repository.GTAccountUserRoleInfoRepository;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import com.example.gtmvcserverside.member.repository.GTUserRoleInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

    private final GTUserRoleInfoRepository userRoleInfoRepository;

    private final GTAccountUserRoleInfoRepository accountUserRoleInfoRepository;

    private final PasswordEncoder passwordEncoder;

    private final EntityManager em;

    /**
     * GT에 가입 비즈니스로직
     * @param joinInRequest 회원가입 요청 DTO
     * @return {@code ResponseEntity<GTJoinInResponse>} 회원가입 응답
     */
    @Transactional
    public ResponseEntity<GTJoinInResponse> joinInGodTongService(final GTJoinInRequest joinInRequest) {

        // check already exist ID & Member
        String requestEmail = joinInRequest.getEmail();
        if (isAlreadyExistEmail(requestEmail)) {
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_ID);
        }

        if (isAlreadyExistMember(joinInRequest)) {
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_MEMBER);
        }

        // save new member
        try{
            GTMemberInfo createdMemberInfo = memberInfoRepository.save(
                    GTMemberInfo.fromJoinInDTO(joinInRequest, passwordEncoder));

            GTAccountUserRoleInfo newAccountUserRoleInfo = new GTAccountUserRoleInfo();
            newAccountUserRoleInfo.addAccountInfo(createdMemberInfo.getAccountInfo());
            newAccountUserRoleInfo.addUserRole(GTUserRole.ROLE_FAN.convertToUserRoleInfo(userRoleInfoRepository));

            accountUserRoleInfoRepository.save(newAccountUserRoleInfo);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(GTJoinInResponse.builder()
                            .description(String.format("%s님의 아이디 '%s'가 성공적으로 생성되었습니다.",
                                    createdMemberInfo.getName(),
                                    createdMemberInfo.getAccountInfo().getAccountEmail()))
                            .createdID(createdMemberInfo.getAccountInfo().getAccountEmail())
                            .createdAt(createdMemberInfo.getCreatedAt())
                            .build());
        } catch (DataIntegrityViolationException ex){
            // if conflict by unique keys
            throw new GTApiException(GTMemberErrorCode.ALREADY_EXIST_MEMBER);
        }

    }

    /**
     * 이미 존재하는 Email(ID) 인지 확인하는 메소드입니다.
     * @param requestEmail Email
     * @return boolean 이미 존재하는 Email 여부
     */
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, readOnly = true)
    public boolean isAlreadyExistEmail(String requestEmail) {
        return accountInfoRepository.existsByAccountEmail(requestEmail);
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
