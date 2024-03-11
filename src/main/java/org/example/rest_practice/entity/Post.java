package org.example.rest_practice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.rest_practice.payload.PostDto;

import java.util.HashSet;
import java.util.Set;

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
public class Post {

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
        return new PostDto(this.postId, this.title, this.description, this.content);
    }

}
