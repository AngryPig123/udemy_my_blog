package org.example.rest_practice.controller.role;

import jakarta.transaction.Transactional;
import org.example.rest_practice.payload.RequestDataContainer;
import org.example.rest_practice.payload.RoleDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * packageName    : org.example.rest_practice.controller.role
 * fileName       : RoleControllerTest
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */
public class RoleControllerTest extends RoleSetup {


    @Test
    @Transactional
    void createRole() throws Exception {
        RoleDto roleDto = new RoleDto(0L, "NOT_DUPLICATE_ROLE");
        roleCreateHelper(roleDto);
    }

}
