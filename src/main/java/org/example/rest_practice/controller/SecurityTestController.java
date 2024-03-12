package org.example.rest_practice.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/security-test")
public class SecurityTestController {

    @PreAuthorize("hasAnyRole('ADMIN','USER','GUEST')")
    @GetMapping(path = "/get-all")
    public String all() {
        return "getAll";
    }

    @GetMapping(path = "/get-admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String getAdmin() {
        return "getAdmin";
    }

    @GetMapping(path = "/get-user")
    @PreAuthorize("hasRole('USER')")
    public String getUser() {
        return "getUser";
    }

    @GetMapping(path = "/get-guest")
    @PreAuthorize("hasRole('GUEST')")
    public String getGuest() {
        return "getGuest";
    }

}
