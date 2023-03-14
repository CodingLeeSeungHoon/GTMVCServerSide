package com.example.gtmvcserverside.member.domain;

import com.example.gtmvcserverside.auth.domain.GTAccountInfo;
import com.example.gtmvcserverside.auth.repository.GTAccountInfoRepository;
import com.example.gtmvcserverside.common.entity.GTBaseEntity;
import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Entity
@Builder
@NoArgsConstructor
public class GTMemberInfo extends GTBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @OneToOne(fetch = FetchType.LAZY)
    private GTAccountInfo accountInfo;

    @Getter
    @Column(nullable = false)
    private String email;

    @Getter
    @Column(nullable = false)
    private int age;

    @Getter
    @Column(nullable = false)
    private LocalDate birthOfDate;

    @Getter
    @Setter
    @Column
    private String nickName;

    @Getter
    @Column(nullable = false)
    private boolean showName;

    @Column(nullable = false)
    private String tel1;

    @Column(nullable = false)
    private String tel2;

    @Column(nullable = false)
    private String tel3;

    public String getFormatPhoneNumber(){
        StringBuffer sb = new StringBuffer();
        return sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
    }

    /**
     *
     * @param joinInRequest
     * @param accountInfoRepository
     * @return
     */
    public static GTMemberInfo fromJoinInDTO(GTJoinInRequest joinInRequest, GTAccountInfoRepository accountInfoRepository) {

        assert joinInRequest.getAccountID() != null;
        assert joinInRequest.getAccountPW() != null;
        assert joinInRequest.getName() != null;
        assert joinInRequest.getEmail() != null;
        assert joinInRequest.getPhoneNumber() != null;
        assert joinInRequest.getDateOfBirth() != null;

        // 계정 ID가 이미 있는지 확인하기
        String requestAccountID = joinInRequest.getAccountID();
        if(!accountInfoRepository.existsByAccountID(requestAccountID)){
            throw new EntityExistsException("이미 존재하는 계정입니다.");
        }

        // Phone format check
        String[] phoneNumParts = joinInRequest.getPhoneNumber().split("-");
        if(phoneNumParts.length != 3){
            throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다 '-'를 포함한 값이 들어와야 합니다.");
        }

        // new account info create.
        GTAccountInfo requestAccountInfo = GTAccountInfo.builder()
                .accountID(joinInRequest.getAccountID())
                .accountPW(joinInRequest.getAccountPW())
                .build();

        // nickname is nullable.
        String requestNickName = joinInRequest.getNickName();
        boolean canShowName = Optional.of(joinInRequest)
                .map(GTJoinInRequest::isShowName)
                .orElse(false);

        return GTMemberInfo.builder()
                .accountInfo(requestAccountInfo)
                .email(joinInRequest.getEmail())
                .age(getAgeFromBirthOfDate(joinInRequest.getDateOfBirth()))
                .birthOfDate(joinInRequest.getDateOfBirth())
                .nickName(requestNickName)
                .showName(canShowName)
                .tel1(phoneNumParts[0])
                .tel2(phoneNumParts[1])
                .tel3(phoneNumParts[2])
                .build();

    }

    private static int getAgeFromBirthOfDate(LocalDate birthOfDate){
        LocalDate curDate = LocalDate.now();
        return Period.between(birthOfDate, curDate).getYears();
    }

}
