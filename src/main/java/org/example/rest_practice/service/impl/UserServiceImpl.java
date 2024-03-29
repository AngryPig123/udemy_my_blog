package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.User;
import org.example.rest_practice.exception.DuplicateResourceException;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.repository.UserRepository;
import org.example.rest_practice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {

        log.info("userDto = {}", userDto);

        Optional<User> byEmail = userRepository.findUserByEmail(userDto.getEmail());

        if (byEmail.isPresent()) {
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

    @Override
    public UserDto getUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId + ""));
        UserDto result = user.toDto();
        log.info("result = {}", result);
        return result;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("user", "email", email));
        UserDto result = user.toDto();
        log.info("result = {}", result);

        return result;
    }
}
