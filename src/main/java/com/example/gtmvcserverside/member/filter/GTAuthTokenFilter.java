package com.example.gtmvcserverside.member.filter;

import com.example.gtmvcserverside.member.domain.GTUserDetails;
import com.example.gtmvcserverside.member.service.GTUserDetailsServiceImpl;
import com.example.gtmvcserverside.member.util.GTJwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class GTAuthTokenFilter extends OncePerRequestFilter {

    private final GTJwtUtil jwtUtil;

    private final GTUserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
                String userName = jwtUtil.getUserNameFromJwtToken(jwt);

                GTUserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (Exception e){
            log.error("Cannot set user authentication : {}", e);
        }

        filterChain.doFilter(request, response);
    }


    private String parseJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7, authHeader.length());
        }

        return null;
    }

}
