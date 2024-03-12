package org.example.rest_practice.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.rest_practice.entity.etc.BaseDate;
import org.example.rest_practice.payload.RoleDto;

@Getter
@Entity
@Table(name = "roles", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseDate {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "name", nullable = false)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    public RoleDto toDto() {
        return new RoleDto(this.roleId, this.name);
    }

}
