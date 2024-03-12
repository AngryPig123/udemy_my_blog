package org.example.rest_practice.service;

import org.example.rest_practice.payload.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(Long userId);

    UserDto getUserByEmail(String email);
}
