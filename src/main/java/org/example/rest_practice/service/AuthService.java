package org.example.rest_practice.service;

import org.example.rest_practice.payload.LoginDto;
import org.example.rest_practice.payload.UserDto;

public interface AuthService {

    String login(LoginDto loginDto);

    UserDto register(UserDto userDto);


}
