package org.example.rest_practice.controller;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.service.AuthService;
import org.example.rest_practice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/users")
public class UserController {

    //  권한 부분과 유저-권한 기능이 완성되기 전까지는 permitAll()로 둔다.
    private final UserService userService;
    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(authService.register(userDto), HttpStatus.CREATED);
    }

    @PreAuthorize("permitAll()")
    @GetMapping(path = "/{userId}")
    public ResponseEntity<UserDto> findUserByUserId(@PathVariable("userId") Long userId) {
        return new ResponseEntity<>(userService.getUserByUserId(userId), HttpStatus.OK);
    }

}
