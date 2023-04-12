package com.example.gtmvcserverside.member.domain;

import com.example.gtmvcserverside.member.enums.GTGender;
import com.example.gtmvcserverside.member.enums.GTUserRole;
import com.example.gtmvcserverside.member.repository.GTAccountInfoRepository;
import com.example.gtmvcserverside.member.repository.GTAccountUserRoleInfoRepository;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class GTMemberEntityTest {

    @Autowired
    private GTAccountInfoRepository accountInfoRepository;

    @Autowired
    private GTMemberInfoRepository memberInfoRepository;

    @Autowired
    private GTAccountUserRoleInfoRepository accountUserRoleInfoRepository;

    private GTAccountInfo mockAccountInfo;

    @BeforeEach
    public void initMockAccountInfo(){
        this.mockAccountInfo = GTAccountInfo.builder()
                .accountEmail("test@example.com")
                .accountPW("1234")
                .build();
    }

    @Test
    @DisplayName("🤔 1. 계정정보 생성 및 저장 테스트 : 성공 케이스")
    public void testForCreateNewAccountInfoAndSave(){
        GTAccountInfo savedAccountInfo = accountInfoRepository.save(mockAccountInfo);
        assertEquals(savedAccountInfo.getAccountEmail(), "test@example.com");
        assertEquals(savedAccountInfo.getAccountPW(), "1234");
        assertEquals(savedAccountInfo.getRoles().size(), 0);

    }

    @Test
    @DisplayName("🤔 2. 멤버정보 생성 및 저장 테스트 : 성공 케이스")
    public void testForCreateNewMemberInfoAndSave(){

        GTMemberInfo mockMemberInfo = GTMemberInfo.builder()
                .accountInfo(mockAccountInfo)
                .age(26)
                .birthOfDate(LocalDate.of(1998, 11, 6))
                .gender(GTGender.Male)
                .name("이승훈")
                .nickName("LSH8569")
                .showName(false)
                .tel1("010")
                .tel2("0000")
                .tel3("0005")
                .build();

        GTMemberInfo savedMemberInfo = memberInfoRepository.save(mockMemberInfo);

        assertEquals(savedMemberInfo.getAccountInfo().getAccountEmail(), "test@example.com");
        assertEquals(savedMemberInfo.getAccountInfo().getAccountPW(), "1234");

        assertEquals(savedMemberInfo.getAge(), 26);
        assertEquals(savedMemberInfo.getBirthOfDate(), LocalDate.of(1998, 11, 6));
        assertEquals(savedMemberInfo.getName(),"이승훈");
        assertEquals(savedMemberInfo.getGender(), GTGender.Male);
        assertEquals(savedMemberInfo.getFormatPhoneNumber(), "010-0000-0005");
        assertEquals(savedMemberInfo.getNickName(), "LSH8569");

        assertNotNull(savedMemberInfo.getCreatedAt());
        assertNotNull(savedMemberInfo.getUpdatedAt());

    }


    @Test
    @DisplayName("🤔 3. 멤버정보 생성 및 저장 테스트 : 계정 정보 없이 등록")
    public void testForCreateNewMemberInfoAndSave_WhenAccountInfoIsNull(){

        // AccountInfo is Null!
        GTMemberInfo mockMemberInfo = GTMemberInfo.builder()
                .age(26)
                .birthOfDate(LocalDate.of(1998, 11, 6))
                .gender(GTGender.Male)
                .name("이승훈")
                .nickName("LSH8569")
                .showName(false)
                .tel1("010")
                .tel2("0000")
                .tel3("0005")
                .build();

        // Because @NotNull, it is not DataIntegrityViolationException
        Exception exception = assertThrows(ConstraintViolationException.class, ()-> memberInfoRepository.save(mockMemberInfo));
        log.info(exception.getMessage());
    }

    @Test
    @DisplayName("🤔 4. 멤버정보 생성 및 저장 테스트 : 이미 있는 계정(이메일) 정보")
    public void testForCreateNewMemberInfoAndSave_WhenAlreadyExistAccountEmail(){

        GTAccountInfo alreadyExistAccountInfo = accountInfoRepository.save(mockAccountInfo);

        GTAccountInfo otherAccountInfo = GTAccountInfo.builder()
                .accountEmail("test@example.com")
                .accountPW("2345")
                .build();

        GTMemberInfo mockMemberInfo = GTMemberInfo.builder()
                .accountInfo(otherAccountInfo)
                .age(26)
                .birthOfDate(LocalDate.of(1998, 11, 6))
                .gender(GTGender.Male)
                .name("이승훈")
                .nickName("LSH8569")
                .showName(false)
                .tel1("010")
                .tel2("0000")
                .tel3("0005")
                .build();

        // DataIntegrityViolationException
        Exception exception = assertThrows(DataIntegrityViolationException.class, ()-> memberInfoRepository.save(mockMemberInfo));
        log.info(exception.getMessage());
    }

    @Test
    @DisplayName("🤔 5. 멤버정보 생성 및 저장 테스트 : 이미 있는 계정(완전 동일) 정보")
    public void testForCreateNewMemberInfoAndSave_WhenAlreadyExistAccount(){

        GTAccountInfo alreadyExistAccountInfo = accountInfoRepository.save(mockAccountInfo);

        GTMemberInfo mockMemberInfo = GTMemberInfo.builder()
                .accountInfo(GTAccountInfo.builder()
                        .accountEmail("test@example.com")
                        .accountPW("1234")
                        .build())
                .age(26)
                .birthOfDate(LocalDate.of(1998, 11, 6))
                .gender(GTGender.Male)
                .name("이승훈")
                .nickName("LSH8569")
                .showName(false)
                .tel1("010")
                .tel2("0000")
                .tel3("0005")
                .build();

        // DataIntegrityViolationException
        Exception exception = assertThrows(DataIntegrityViolationException.class, ()-> memberInfoRepository.save(mockMemberInfo));
        log.info(exception.getMessage());
    }

    @Test
    @DisplayName("🤔 6. 멤버정보 생성 및 저장 테스트 : 이미 있는 계정(완전 동일) 정보 2")
    public void testForCreateNewMemberInfoAndSave_WhenAlreadyExistAccount2(){

        GTMemberInfo mockMemberInfo = GTMemberInfo.builder()
                .accountInfo(mockAccountInfo)
                .age(26)
                .birthOfDate(LocalDate.of(1998, 11, 6))
                .gender(GTGender.Male)
                .name("이승훈")
                .nickName("LSH8569")
                .showName(false)
                .tel1("010")
                .tel2("0000")
                .tel3("0005")
                .build();

        memberInfoRepository.save(mockMemberInfo);

        GTAccountInfo mockAccountInfo2 = GTAccountInfo.builder()
                .accountEmail("test@example.com")
                .accountPW("1234")
                .build();


        GTMemberInfo mockMemberInfo2 = GTMemberInfo.builder()
                .accountInfo(mockAccountInfo2)
                .age(22)
                .birthOfDate(LocalDate.of(1998, 12, 6))
                .gender(GTGender.Male)
                .name("이훈")
                .nickName("LS8569")
                .showName(false)
                .tel1("010")
                .tel2("0100")
                .tel3("0005")
                .build();

        // DataIntegrityViolationException
        Exception exception = assertThrows(DataIntegrityViolationException.class, ()-> memberInfoRepository.save(mockMemberInfo2));
        log.info(exception.getMessage());
    }

    @Test
    @DisplayName("🤔 7. 계정-유저 권한 생성 및 저장 테스트 : 성공 케이스")
    public void testForCreateNewAccountUserRoleInfoAndSave(){
        GTAccountUserRoleInfo accountUserRoleInfo = new GTAccountUserRoleInfo();
    }

}