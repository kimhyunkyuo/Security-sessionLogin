package com.example.study.config;

import com.example.study.oauth2.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final PrincipalOauth2UserService principalOauth2UserService;
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/","/login","/join","/oauth2/authorization/google").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                        .anyRequest().authenticated()
                );
        http
                .formLogin((form) -> form
                        .loginPage("/login")
                        .usernameParameter("loginId")
                        .passwordParameter("userPassword")
                        .loginProcessingUrl("/login")
                        .failureUrl("/login")
                        .permitAll()
        );
        http
                .oauth2Login((oauth2) -> oauth2
                        .loginPage("/oauth2/authorization/google")
                        .defaultSuccessUrl("/")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint.userService(principalOauth2UserService))
                );
        http
                .csrf((auth) -> auth.disable()

                );
        return http.build();
    }
}
