package org.example.rest_practice.payload;

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
    private String name;
    private String email;
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
