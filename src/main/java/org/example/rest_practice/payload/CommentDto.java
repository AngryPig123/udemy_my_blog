package org.example.rest_practice.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.rest_practice.entity.Comment;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class CommentDto {

    private Long commentId;

    @NotEmpty(message = "Name should not be null or empty")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be null or empty")
    private String email;

    @NotEmpty
    @Size(min = 10, message = "Comment body must be minimum 10 characters")
    private String body;

    public CommentDto(Long commentId, String name, String email, String body) {
        this.commentId = commentId;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public Comment toEntity() {
        return new Comment(this.name, this.email, this.body);
    }

}
