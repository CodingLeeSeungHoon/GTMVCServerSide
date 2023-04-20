package com.example.gtmvcserverside.member.config;

import com.example.gtmvcserverside.member.filter.GTAuthTokenFilter;
import com.example.gtmvcserverside.member.filter.GTJwtAuthEntryPoint;
import com.example.gtmvcserverside.member.service.GTUserDetailsServiceImpl;
import com.example.gtmvcserverside.member.util.GTJwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 갓통(GodTong)의 인증 설정 클래스입니다. <br>
 * JWT Token 기반 인증
 */
@Order(1)
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class GTAuthConfig extends WebSecurityConfigurerAdapter {

    private final GTUserDetailsServiceImpl userDetailsService;

    private final GTJwtAuthEntryPoint unauthorizedHandler;

    private final GTJwtUtil jwtUtil;


    @Bean
    public GTAuthTokenFilter authenticationJwtTokenFilter() {
        return new GTAuthTokenFilter(jwtUtil, userDetailsService);
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


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
        http.cors().and().csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/api/auth/**").permitAll()
                .antMatchers("/api/test/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
