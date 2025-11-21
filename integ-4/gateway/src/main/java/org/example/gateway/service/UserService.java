package org.example.gateway.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.example.gateway.infraestructure.feign.UserFeignClient;
import org.example.gateway.service.dto.user.UserDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserFeignClient userFeign;
    private final PasswordEncoder passwordEncoder;

    public void register(UserDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        userFeign.save(dto);
    }
}

