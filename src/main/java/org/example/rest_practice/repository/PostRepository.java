package org.example.rest_practice.repository;

import org.example.rest_practice.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : org.example.rest_practice.repository
 * fileName       : PostRepository
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */
public interface PostRepository extends JpaRepository<Post, Long> {
    
}
