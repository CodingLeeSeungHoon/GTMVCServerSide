package com.example.gtmvcserverside.member.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


/**
 * 사용자 Role과 계정을 M:N 관계 매핑을 위한 매핑 테이블 클래스
 */
@Slf4j
@Builder
@Data
@Entity
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class GTAccountUserRoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    @Setter(AccessLevel.NONE)
    private GTUserRoleInfo userRoleInfo;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    @Setter(AccessLevel.NONE)
    private GTAccountInfo accountInfo;

    /**
     * UserRole Setter 컨비니언스 메소드
     * @param userRoleInfo
     */
    public void addUserRole(GTUserRoleInfo userRoleInfo){
        if(this.userRoleInfo != null && !this.userRoleInfo.equals(userRoleInfo)){
            this.userRoleInfo.getAccountWithThisRoleList().remove(this);
        }
        this.userRoleInfo = userRoleInfo;
        if(!userRoleInfo.getAccountWithThisRoleList().contains(this)){
            userRoleInfo.getAccountWithThisRoleList().add(this);
        }
    }


    public void addAccountInfo(GTAccountInfo accountInfo){
        if(this.accountInfo != null && !this.accountInfo.equals(this)){
            this.accountInfo.getRoleByThisAccountList().remove(this);
        }
        this.accountInfo = accountInfo;
        if(!accountInfo.getRoleByThisAccountList().contains(this)){
            accountInfo.getRoleByThisAccountList().add(this);
        }
    }

}
