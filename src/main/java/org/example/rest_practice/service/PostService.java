package org.example.rest_practice.service;

import org.example.rest_practice.payload.PostDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * packageName    : org.example.rest_practice.service
 * fileName       : PostService
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */
public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();
}
