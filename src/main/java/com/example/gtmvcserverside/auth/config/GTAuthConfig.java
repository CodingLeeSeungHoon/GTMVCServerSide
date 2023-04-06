package com.example.gtmvcserverside.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 갓통(GodTong)의 인증 설정 클래스입니다. <br>
 * JWT Token 기반 인증
 */
@Order(1)
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GTAuthConfig extends WebSecurityConfigurerAdapter {

    /**
     * GT의 Role 계층 구조 정의 <br>
     * 일반적으로 ADMIN > STAR > MANAGER > FAN 의 계층 구조 성립 <br>
     * 예외의 경우는 별도의 비즈니스 로직으로 처리.
     *
     * @return {@code RoleHierarchy}
     */
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_STAR > ROLE_MANAGER > ROLE_FAN");
        return roleHierarchy;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }
}
