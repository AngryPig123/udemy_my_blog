package org.example.rest_practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.payload.LoginDto;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = {"/login", "/signIn"})
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        return new ResponseEntity<>(authService.login(loginDto), HttpStatus.OK);
    }

    @PostMapping(path = "/register")
    public ResponseEntity<UserDto> register(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.register(userDto), HttpStatus.CREATED);
    }

}
