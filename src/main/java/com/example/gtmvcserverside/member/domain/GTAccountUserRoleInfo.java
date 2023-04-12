package com.example.gtmvcserverside.member.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;


/**
 * 사용자 Role과 계정을 M:N 관계 매핑을 위한 매핑 테이블 클래스
 */
@Data
@Entity
public class GTAccountUserRoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    @Setter(AccessLevel.NONE)
    private GTUserRoleInfo userRole;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private GTAccountInfo accountInfo;

    /**
     * UserRole Setter 컨비니언스 메소드
     * @param userRole
     */
    public void setUserRole(GTUserRoleInfo userRole){
        this.userRole = userRole;
        userRole.getAccountWithThisRoleList().add(this);
    }

}
