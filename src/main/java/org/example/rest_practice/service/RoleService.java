package org.example.rest_practice.service;

import org.example.rest_practice.payload.RoleDto;

/**
 * packageName    : org.example.rest_practice.service
 * fileName       : RoleService
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */
public interface RoleService {

    RoleDto createRole(RoleDto roleDto);

    RoleDto findRoleByName(String name);

}
