package org.example.rest_practice.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.rest_practice.payload.CommentDto;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class Comment {

    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "body", nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public void setPostOnComments(Post post) {
        this.post = post;
    }

    public Comment(String name, String email, String body) {
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public CommentDto toDto() {
        return new CommentDto(this.commentId, this.name, this.email, this.body);
    }

}
