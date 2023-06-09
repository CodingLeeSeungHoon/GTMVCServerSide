package com.example.gtmvcserverside.member.domain;

import com.example.gtmvcserverside.common.entity.GTBaseEntity;
import com.example.gtmvcserverside.common.enums.GTMemberErrorCode;
import com.example.gtmvcserverside.common.exception.GTApiException;
import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.enums.GTGender;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

@Entity(name = "gt_member_info")
@Builder
@Table(name = "gt_member_info",
        uniqueConstraints = {@UniqueConstraint(
                name = "member_unique_1",
                columnNames = {"name", "tel1", "tel2", "tel3"})}
)
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GTMemberInfo extends GTBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    @JoinColumn(name = "account_id", unique = true)
    private GTAccountInfo accountInfo;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 4)
    private int age;

    @Column(nullable = false)
    private LocalDate birthOfDate;

    @Setter
    @Column(length = 20)
    private String nickName;

    @Enumerated(EnumType.ORDINAL)
    @Column
    private GTGender gender;

    @Column(nullable = false)
    private boolean showName;

    @Getter(AccessLevel.NONE)
    @Column(nullable = false, length = 3)
    private String tel1;

    @Getter(AccessLevel.NONE)
    @Column(nullable = false, length = 4)
    private String tel2;

    @Getter(AccessLevel.NONE)
    @Column(nullable = false, length = 4)
    private String tel3;

    /**
     * 엔티티의 tel1~3으로부터 전화번호 형식으로 포매팅한 전화번호 문자열을 리턴하는 메소드
     *
     * @return {@code String} 포매팅 전화번호
     */
    public String getFormatPhoneNumber() throws IllegalStateException {
        if (tel1 == null || tel2 == null || tel3 == null) {
            throw new IllegalStateException("엔티티 객체 내에 저장된 전화번호가 없습니다, 다시 확인해주세요.");
        }
        StringBuilder sb = new StringBuilder();
        return sb.append(tel1).append("-").append(tel2).append("-").append(tel3).toString();
    }

    /**
     * {@code GTJoinInRequest} DTO로 부터 엔티티 객체를 생성할 수 있는 스태틱 메소드
     *
     * @param joinInRequest 회원가입 요청 객체
     * @return {@code GTMemberInfo} 회원 정보 엔티티 객체
     */
    public static GTMemberInfo fromJoinInDTO(
            @Validated GTJoinInRequest joinInRequest,
            PasswordEncoder passwordEncoder) throws GTApiException {

        // Phone format check
        String[] phoneNumParts = joinInRequest.getPhoneNumber().split("-");
        if (phoneNumParts.length != 3) {
            throw new GTApiException(GTMemberErrorCode.INVALID_PHONE_FORMAT);
        }

        String encodedPassword = passwordEncoder.encode(joinInRequest.getAccountPW());

        // new account info create.
        GTAccountInfo requestAccountInfo = GTAccountInfo.builder()
                .accountEmail(joinInRequest.getEmail())
                .accountPW(encodedPassword)
                .build();

        // nickname is nullable.
        String requestNickName = joinInRequest.getNickName();

        boolean canShowName = Optional.of(joinInRequest)
                .map(GTJoinInRequest::isShowName)
                .orElse(false);

        GTGender gender = joinInRequest.getGender();

        return GTMemberInfo.builder()
                .accountInfo(requestAccountInfo)
                .age(getAgeFromBirthOfDate(joinInRequest.getDateOfBirth()))
                .birthOfDate(joinInRequest.getDateOfBirth())
                .nickName(requestNickName)
                .showName(canShowName)
                .gender(gender)
                .tel1(phoneNumParts[0])
                .tel2(phoneNumParts[1])
                .tel3(phoneNumParts[2])
                .build();
    }


    /**
     * 생년월일 데이터로부터 나이를 구하는 메소드
     *
     * @param birthOfDate {@code LocalDate} 타입의 생년월일
     * @return {@code int} 나이
     */
    private static int getAgeFromBirthOfDate(LocalDate birthOfDate) {
        LocalDate curDate = LocalDate.now();
        return Period.between(birthOfDate, curDate).getYears();
    }

}
