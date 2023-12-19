package com.example.study.repository;

import com.example.study.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    // 이메일로 회원 정보 조회
    boolean existsByLoginId(String loginId);
    Optional<UserEntity> findByLoginId(String loginId);
}
