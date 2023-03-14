package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.auth.repository.GTAccountInfoRepository;
import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import com.example.gtmvcserverside.member.dto.GTJoinInRequest;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class GTMemberServiceImpl implements GTMemberService{

    private final GTMemberInfoRepository memberInfoRepository;

    private final GTAccountInfoRepository accountInfoRepository;

    public void joinInGodTongService(final GTJoinInRequest joinInRequest){
        try{
            GTMemberInfo newRequestMember = GTMemberInfo.fromJoinInDTO(joinInRequest, accountInfoRepository);
            memberInfoRepository.save(newRequestMember);
        } catch (Exception e){

        }
    }

}
