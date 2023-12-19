package com.example.study.dto;

import com.example.study.entity.UserEntity;
import com.example.study.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class JoinDTO {
    @NotBlank(message =  "아이디는 필수입니다.")
    private String loginId;
    private String userPassword;
    private String userPassWordCheck;
    private String userName;

    public UserEntity toEntity(String encodedPassword){

       return UserEntity.builder()
               .loginId(this.loginId)
               .userPassword(encodedPassword)
               .userName(this.userName)
               .role(UserRole.USER)
               .build();
    }
}
