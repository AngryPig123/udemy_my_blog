package org.example.rest_practice.controller.user;

import org.example.rest_practice.payload.UserDto;
import org.junit.jupiter.api.*;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest extends UserSetup {

    @Test
    @Order(1)
    @Transactional
    void createUser() throws Exception {
        UserDto userDto = new UserDto(0L, "홍길동", "test@gmail.com", "1q2w3e4!");
        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpect(status().isCreated());

        mockMvc.perform(
                        post("/api/v1/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDto))
                )
                .andExpect(status().isConflict());
    }

    @Test
    @Order(2)
    @Transactional
    void findUserByUserId() throws Exception {
        UserDto userDto = new UserDto(0L, "홍길동", "test@gmail.com", "1q2w3e4!");
        UserDto saveUserDto = userCreateHelper(userDto);
        mockMvc.perform(
                        get("/api/v1/users/{userId}", saveUserDto.getUserId())
                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/users/{userId}", NOT_FOUND_ID)
                )
                .andExpect(status().isNotFound());

    }


}
