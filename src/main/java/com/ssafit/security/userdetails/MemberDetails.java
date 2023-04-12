package com.ssafit.security.userdetails;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
public class MemberDetails implements UserDetails {
    private final String studentId;

    private final String nickname;

    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    public MemberDetails(final String studentId, final String nickname, final String... authorities) {
        this.studentId = studentId;
        this.nickname = nickname;
        this.authorities = (authorities == null) ? (null) : (AuthorityUtils.createAuthorityList(authorities));
    }

    public String getStudentId() {
        return studentId;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
}