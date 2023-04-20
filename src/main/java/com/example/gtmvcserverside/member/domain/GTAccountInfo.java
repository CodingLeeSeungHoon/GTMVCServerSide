package com.example.gtmvcserverside.member.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.*;

/**
 * 계정 정보(Email, PW, 역할(권한))을 포함한 엔티티입니다.
 * - {@code GTUserRoleInfo}와 N:M 관계 매핑, {@code GTAccountUserRoleInfo} 테이블을 활용해 N:1, 1:M으로 분리
 *
 */
@Slf4j
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gt_account_info")
public class GTAccountInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private long id;

    @Getter
    @Column(name = "account_email", unique = true)
    private String accountEmail;

    @Getter
    @Column(name = "account_pw")
    private String accountPW;

    @Builder.Default
    @OneToMany(mappedBy = "accountInfo", fetch = FetchType.LAZY)
    private List<GTAccountUserRoleInfo> roleByThisAccountList = new ArrayList<>();


    /**
     * 계정 정보에 역할을 추가하기 위한 컨비니언스 메서드입니다.<br>
     * @param accountUserRoleInfo 계정정보와 GTUserRole 이 담긴 객체
     */
    public void addRole(GTAccountUserRoleInfo accountUserRoleInfo){
        accountUserRoleInfo.addAccountInfo(this);
        this.roleByThisAccountList.add(accountUserRoleInfo);
    }

}
