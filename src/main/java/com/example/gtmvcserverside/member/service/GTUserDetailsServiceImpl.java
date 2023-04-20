package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.domain.GTMemberInfo;
import com.example.gtmvcserverside.member.domain.GTUserDetails;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GTUserDetailsServiceImpl implements GTUserDetailsService {

    private final GTMemberInfoRepository memberInfoRepository;

    @Override
    public GTUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        GTMemberInfo memberInfo = memberInfoRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with name : " + username));

        return GTUserDetails.build(memberInfo);
    }
}
