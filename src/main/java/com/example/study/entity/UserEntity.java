package com.example.study.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_table")
public class UserEntity {
    @Id //pk지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 시퀀스
    private  Long id;

    @Column
    private String loginId;

    @Column
    private String userPassword;

    @Column
    private String userName;

    private UserRole role;


    }

