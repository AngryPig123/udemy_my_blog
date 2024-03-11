package org.example.rest_practice.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;

/**
 * packageName    : org.example.rest_practice.payload
 * fileName       : PostDto
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */

@Data
@ToString
@NoArgsConstructor
public class PostDto {

    private Long postId;

    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> commentDtoSet;

    public PostDto(Long postId, String title, String description, String content, Set<CommentDto> commentDtoSet) {
        this.postId = postId;
        this.title = title;
        this.description = description;
        this.content = content;
        this.commentDtoSet = commentDtoSet;
    }

}
