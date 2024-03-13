package org.example.rest_practice.controller.auth;

import org.example.rest_practice.payload.UserDto;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthControllerTest extends AuthSetup {

    @Test
    @Order(1)
    @Transactional
    void login() throws Exception {
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(admin))
                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(fakeUser))
                )
                .andExpect(status().isUnauthorized());

        admin.setPassword("not_match_password");
        mockMvc.perform(
                        post("/api/v1/auth/login")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(admin))
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Order(2)
    @Transactional
    void register() throws Exception {

        UserDto registUserDto = new UserDto(
                0L, "홍길동", "ghdrlfehd@gmail.com", "1q2w3e4r!"
        );

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(registUserDto))
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        post("/api/v1/auth/register")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(registUserDto))
                )
                .andExpect(status().isConflict());

    }

}
