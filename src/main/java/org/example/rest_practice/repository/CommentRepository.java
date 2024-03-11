package org.example.rest_practice.repository;

import org.example.rest_practice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Comment findCommentByEmail(String email);

}
