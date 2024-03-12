package org.example.rest_practice.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.rest_practice.entity.etc.BaseDate;
import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.payload.PostDto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * packageName    : org.example.rest_practice.entity
 * fileName       : Post
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Post extends BaseDate {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "content", nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)  //
    private Set<Comment> comments = new HashSet<>();

    public Post(String title, String description, String content) {
        this.title = title;
        this.description = description;
        this.content = content;
    }

    public PostDto toDto() {
        Set<CommentDto> commentDtoSet = this.comments.stream().map(Comment::toDto).collect(Collectors.toSet());
        return new PostDto(this.postId, this.title, this.description, this.content, commentDtoSet);
    }

}
