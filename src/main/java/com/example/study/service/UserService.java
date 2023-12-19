package com.example.study.service;

import com.example.study.dto.JoinDTO;
import com.example.study.dto.LoginDTO;
import com.example.study.entity.UserEntity;
import com.example.study.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    public boolean checkLoginIdDuplicate(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public void join(JoinDTO req) {
        userRepository.save(req.toEntity(encoder.encode(req.getUserPassword())));

    }

    public UserEntity login(LoginDTO req) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByLoginId(req.getLoginId());

        if (optionalUserEntity.isEmpty()) {
            return null;
        }
        UserEntity userEntity = optionalUserEntity.get();

        if (!userEntity.getUserPassword().equals(req.getUserPassword())) {
            return null;
        }
        return userEntity;
    }

    public UserEntity getLoginUserById(Long userId) {
        if (userId == null) return null;

        Optional<UserEntity> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }

    public UserEntity getLoginUserByLoginId(String loginId) {
        if (loginId == null) return null;

        Optional<UserEntity> optionalUser = userRepository.findByLoginId(loginId);
        if (optionalUser.isEmpty()) return null;

        return optionalUser.get();
    }
}


