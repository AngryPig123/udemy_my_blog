package org.example.rest_practice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/security-test")
public class SecurityTestController {

    @GetMapping
    public String securityGetMethodTest() {
        return "security-get";
    }

}
