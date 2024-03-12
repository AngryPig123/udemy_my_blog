package org.example.rest_practice.payload;

import lombok.*;
import org.example.rest_practice.entity.Role;

/**
 * packageName    : org.example.rest_practice.payload
 * fileName       : RoleDto
 * author         : AngryPig123
 * date           : 2024-03-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-12        AngryPig123       최초 생성
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long roleId;
    private String name;

    public Role toEntity() {
        return new Role(this.name);
    }

}
