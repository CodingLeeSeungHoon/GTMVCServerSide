package com.example.gtmvcserverside.member.domain;

import com.example.gtmvcserverside.member.enums.GTUserRole;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * GT 회원의 권한과 관련된 역할 규정을 위한 엔티티 클래스 <br>
 * userRole에는 {@code GTUserRole} Enum Type이 들어간다. <br>
 */
@Slf4j
@Getter
@Builder
@Entity
@Table(name = "gt_user_roles")
@NoArgsConstructor
@AllArgsConstructor
public class GTUserRoleInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, name = "user_role", unique = true)
    private GTUserRole userRole;

    @OneToMany(mappedBy = "userRoleInfo")
    @Builder.Default
    private List<GTAccountUserRoleInfo> accountWithThisRoleList = new ArrayList<>();

}
