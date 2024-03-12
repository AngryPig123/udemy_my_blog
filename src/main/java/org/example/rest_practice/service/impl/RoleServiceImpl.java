package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.Role;
import org.example.rest_practice.exception.DuplicateResourceException;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.example.rest_practice.payload.RoleDto;
import org.example.rest_practice.repository.RoleRepository;
import org.example.rest_practice.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * packageName    : org.example.rest_practice.service.impl
 * fileName       : RoleServiceImpl
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public RoleDto createRole(RoleDto roleDto) {
        Optional<Role> findRole = roleRepository.findByName(roleDto.getName());
        if (findRole.isPresent()) {
            throw new DuplicateResourceException("role", "name", roleDto.getName());
        } else {
            Role entity = roleDto.toEntity();
            Role saveRole = roleRepository.save(entity);
            RoleDto result = saveRole.toDto();
            log.info("result = {}", result);
            return result;
        }
    }

    @Override
    public RoleDto findRoleByName(String name) {
        Role findRole = roleRepository.findByName(name).orElseThrow(
                () -> new ResourceNotFoundException("role", "name", name)
        );
        RoleDto result = findRole.toDto();
        log.info("result = {}", result);
        return result;
    }
}
