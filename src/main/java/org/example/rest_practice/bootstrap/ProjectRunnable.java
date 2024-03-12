package org.example.rest_practice.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.payload.RoleDto;
import org.example.rest_practice.payload.UserDto;
import org.example.rest_practice.service.RoleService;
import org.example.rest_practice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * packageName    : org.example.rest_practice.bootstrap
 * fileName       : ProjectRunnable
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectRunnable implements CommandLineRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final ResourceLoader resourceLoader;
    private final JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        roleDataInit();
        userDataInit();

        UserDto admin = userService.getUserByEmail("admin@gmail.com");
        UserDto user = userService.getUserByEmail("user@gmail.com");
        UserDto guest = userService.getUserByEmail("guest@gmail.com");

        RoleDto admin_auth = roleService.findRoleByName("ADMIN");
        RoleDto user_auth = roleService.findRoleByName("USER");
        RoleDto guest_auth = roleService.findRoleByName("GUEST");

        usersRolesDataInit(admin.getUserId(), admin_auth.getRoleId());
        usersRolesDataInit(user.getUserId(), user_auth.getRoleId());
        usersRolesDataInit(guest.getUserId(), guest_auth.getRoleId());

    }

    private void usersRolesDataInit(Long userId, Long roleId) {
        jdbcTemplate.execute(String.format("insert into users_roles(user_id,role_id) values(%s,%s)", userId, roleId));
    }

    private void userDataInit() {
        Resource resource = resourceLoader.getResource(String.format("classpath:bootstrap/%s", "user.csv"));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                UserDto userDto = new UserDto(0L, split[0], split[1], split[2]);
                userService.createUser(userDto);
            }
        } catch (IOException io) {
            log.error("IOException = ", io);
        }
    }

    private void roleDataInit() {
        Resource resource = resourceLoader.getResource(String.format("classpath:bootstrap/%s", "role.csv"));
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                RoleDto roleDto = new RoleDto(0L, line);
                roleService.createRole(roleDto);
            }
        } catch (IOException io) {
            log.error("IOException = ", io);
        }
    }

}
