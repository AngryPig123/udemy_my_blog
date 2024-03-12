package org.example.rest_practice.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.rest_practice.entity.etc.BaseDate;
import org.example.rest_practice.entity.etc.UserRoleId;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "users_roles")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserRole extends BaseDate {

    @EmbeddedId
    private UserRoleId id;

    @MapsId("userId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @MapsId("roleId")
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "role_id")
    private Role roleId;

}
