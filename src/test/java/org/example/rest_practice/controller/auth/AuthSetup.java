package org.example.rest_practice.controller.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.payload.LoginDto;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@AutoConfigureMockMvc
public abstract class AuthSetup {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected LoginDto admin;

    protected LoginDto user;

    protected LoginDto guest;

    protected LoginDto fakeUser;

    @BeforeEach
    void beforeEach(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
        admin = new LoginDto("admin@gmail.com", "1q2w3e4r!");
        user = new LoginDto("user@gmail.com", "1q2w3e4r!");
        guest = new LoginDto("guest@gmail.com", "1q2w3e4r!");
        fakeUser = new LoginDto("fakeUser@gmail.com", "fakeUserfakeUser");
    }

}
