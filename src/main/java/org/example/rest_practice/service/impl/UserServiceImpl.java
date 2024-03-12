package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.User;
import org.example.rest_practice.exception.DuplicateResourceException;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.repository.UserRepository;
import org.example.rest_practice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        boolean emailCheck = userRepository.findByEmail(userDto.getEmail()).isPresent();

        if (emailCheck) {
            throw new DuplicateResourceException("user", "email", userDto.getEmail());
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
