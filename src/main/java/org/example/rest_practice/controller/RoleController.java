package org.example.rest_practice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.payload.RoleDto;
import org.example.rest_practice.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * packageName    : org.example.rest_practice.controller
 * fileName       : RoleController
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/roles")
public class RoleController {

    //  생성
    private final RoleService roleService;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<RoleDto> createRole(
            @RequestBody RoleDto roleDto
    ) {
        return new ResponseEntity<>(roleService.createRole(roleDto), HttpStatus.CREATED);
    }

}
