package com.example.gtmvcserverside.member.service;

import com.example.gtmvcserverside.member.dto.GTAuthLoginRequest;
import com.example.gtmvcserverside.member.dto.GTAuthLoginResponse;
import com.example.gtmvcserverside.member.repository.GTAccountUserRoleInfoRepository;
import com.example.gtmvcserverside.member.repository.GTMemberInfoRepository;
import com.example.gtmvcserverside.member.util.GTJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GTAuthServiceImpl implements GTAuthService{

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final GTJwtUtil jwtUtil;

    private final GTMemberInfoRepository memberInfoRepository;

    private final GTAccountUserRoleInfoRepository accountUserRoleInfoRepository;


    @Override
    public ResponseEntity<?> authenticateUser(GTAuthLoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getAccountEmail(), loginRequest.getAccountPW())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateAccessToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok()
                .body(new GTAuthLoginResponse(jwt));
    }

}
