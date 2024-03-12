package org.example.rest_practice.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class SecuritySetup {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected UserDetailsService userDetailsService;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    protected UserDetails adminDetails;

    protected UserDetails userDetails;

    protected UserDetails guestDetails;

    @BeforeEach
    void beforeEach(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

        adminDetails = userDetailsService.loadUserByUsername("admin");
        Assertions.assertNotNull(adminDetails);

        userDetails = userDetailsService.loadUserByUsername("user");
        Assertions.assertNotNull(userDetails);

        guestDetails = userDetailsService.loadUserByUsername("guest");
        Assertions.assertNotNull(guestDetails);

    }

}
