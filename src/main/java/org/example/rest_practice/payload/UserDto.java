package org.example.rest_practice.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.example.rest_practice.entity.User;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long userId;

    @NotEmpty
    @Size(min = 2)
    private String name;

    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 6, max = 25)
    private String password;


    public User toEntity() {
        return new User(this.name, this.email, this.password);
    }

}
