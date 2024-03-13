package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.User;
import org.example.rest_practice.exception.DuplicateResourceException;
import org.example.rest_practice.payload.LoginDto;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.repository.UserRepository;
import org.example.rest_practice.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return "User logged in success";
    }

    @Override
    public UserDto register(UserDto userDto) {

        log.info("userDto = {}", userDto);
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("auth register", "email", userDto.getEmail());
        } else {
            userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
            User saveEntity = userDto.toEntity();
            User save = userRepository.save(saveEntity);
            UserDto result = save.toDto();
            log.info("result = {}", result);
            return result;
        }

    }

}
