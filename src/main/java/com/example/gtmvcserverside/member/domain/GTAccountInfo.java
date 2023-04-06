package com.example.gtmvcserverside.member.domain;

import com.example.gtmvcserverside.member.dto.GTAuthLoginRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Optional;


@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gt_account_info")
public class GTAccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    @Column(name = "account_id")
    private String accountID;

    @Getter
    @Column(name = "account_pw")
    private String accountPW;

    /**
     * {@code GTAuthLoginRequest}로 부터 Entity를 만들어내는 정적 팩토리 메서드<br>
     * 내부에 validation 로직이 포함되어 있음. <br>
     * @param loginRequestDTO 로그인 요청 DTO
     * @return {@code GTAccountInfo} 계정 정보 엔티티
     * @throws IllegalArgumentException 부적절한 요청에 대한 예외 방출
     */
    public static GTAccountInfo fromLoginDTO(GTAuthLoginRequest loginRequestDTO) throws IllegalArgumentException{

        // validation
        String requestID = Optional.ofNullable(loginRequestDTO)
                .map(GTAuthLoginRequest::getAccountID)
                .orElseThrow(() -> new IllegalArgumentException("요청에 ID 값을 포함하고 있지 않습니다."));

        String requestPW = Optional.of(loginRequestDTO)
                .map(GTAuthLoginRequest::getAccountPW)
                .orElseThrow(() -> new IllegalArgumentException("요청에 PW 값을 포함하고 있지 않습니다."));

        // return
        return GTAccountInfo.builder()
                .accountID(requestID)
                .accountPW(requestPW)
                .build();
    }


}
