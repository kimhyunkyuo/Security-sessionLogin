package com.example.study.auth;

import com.example.study.entity.UserEntity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrincipalDetails implements UserDetails, OAuth2User {

    private UserEntity userEntity;

    public PrincipalDetails(UserEntity userEntity){
        this.userEntity = userEntity;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collections = new ArrayList<>();
        collections.add(() -> {
            return userEntity.getRole().name();
        });

        return collections;
    }
    // get Password 메서드
    @Override
    public String getPassword() {
        return userEntity.getUserPassword();
    }

    // get Username 메서드 (생성한 User은 loginId 사용)
    @Override
    public String getUsername() {
        return userEntity.getLoginId();
    }

    // 계정이 만료 되었는지 (true: 만료X)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠겼는지 (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되었는지 (true: 만료X)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성화(사용가능)인지 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }

    //OAuth 로그인
    private Map<String,Object> attributes;

    public PrincipalDetails(UserEntity userEntity,Map<String, Object> attributes){
        this.userEntity = userEntity;
        this.attributes = attributes;
    }
    @Override
    public String getName(){
        return null;
    }

    @Override
    public Map<String, Object>getAttributes(){
        return attributes;
    }

}
