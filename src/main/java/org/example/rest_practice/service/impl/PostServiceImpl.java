package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.Post;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.repository.PostRepository;
import org.example.rest_practice.service.PostService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * packageName    : org.example.rest_practice.service.impl
 * fileName       : PostServiceImpl
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
        Post save = postRepository.save(post);
        PostDto postResponse = new PostDto(save.getId(), save.getTitle(), save.getDescription(), save.getContent());
        log.info("postResponse = {}", postResponse);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<PostDto> postDtoList = postRepository.findAll().stream().map(Post::toDto).collect(Collectors.toList());
        log.info("postDtoList = {}", postDtoList);
        return postDtoList;
    }

}
