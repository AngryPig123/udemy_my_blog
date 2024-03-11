package org.example.rest_practice.service;

import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.payload.PostResponse;

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

    PostResponse getAllPosts(int pageNo, int pageSize, String sort, String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    PostDto deletePost(Long id);

}
