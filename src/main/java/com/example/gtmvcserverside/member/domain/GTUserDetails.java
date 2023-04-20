package com.example.gtmvcserverside.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@AllArgsConstructor
public class GTUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Getter
    private Long id;

    @Getter
    private String username;

    @Getter
    private String email;

    @Getter
    @JsonIgnore
    private String password;

    @Getter
    private Collection<? extends GrantedAuthority> authorities;


    public static GTUserDetails build(GTMemberInfo memberInfo) {
        List<GrantedAuthority> authorities = memberInfo.getAccountInfo().getRoleByThisAccountList().stream()
                .map(accountUserRoleInfo -> new SimpleGrantedAuthority(accountUserRoleInfo.getUserRoleInfo().getUserRole().name()))
                .collect(Collectors.toList());

        return new GTUserDetails(
                memberInfo.getId(),
                memberInfo.getName(),
                memberInfo.getAccountInfo().getAccountEmail(),
                memberInfo.getAccountInfo().getAccountPW(),
                authorities
        );
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GTUserDetails user = (GTUserDetails) o;
        return Objects.equals(id, user.id);
    }
}
