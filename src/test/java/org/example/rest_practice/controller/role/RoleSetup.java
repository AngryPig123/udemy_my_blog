package org.example.rest_practice.controller.role;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.payload.RoleDto;
import org.example.rest_practice.service.RoleService;
import org.example.rest_practice.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : org.example.rest_practice.controller.role
 * fileName       : RoleSetup
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */

@SpringBootTest
@AutoConfigureMockMvc
public abstract class RoleSetup {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected RoleService roleService;

    protected final Long NOT_FOUND_ID = Long.MAX_VALUE - 2;

    @BeforeEach
    void beforeEach(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();
//        jdbcTemplate.execute("DELETE FROM roles");
    }

    protected RoleDto roleCreateHelper(RoleDto roleDto) throws Exception {
        mockMvc.perform(
                        post("/api/v1/roles")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(roleDto))
                )
                .andExpect(status().isCreated());

        RoleDto result = roleService.findRoleByName(roleDto.getName());
        Assertions.assertNotNull(result);
        return result;
    }


}
