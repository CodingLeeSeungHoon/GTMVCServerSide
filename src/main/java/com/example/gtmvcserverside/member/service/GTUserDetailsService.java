package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.domain.GTUserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface GTUserDetailsService extends UserDetailsService {
    GTUserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
