package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.Post;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.payload.PostResponse;
import org.example.rest_practice.repository.PostRepository;
import org.example.rest_practice.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getDescription(), postDto.getContent());
        Post save = postRepository.save(post);
        PostDto postResponse = save.toDto();
        log.info("postResponse = {}", postResponse);
        return postResponse;
    }

    @Override
    public PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {


        //  솔트 조건이 여러개인 경우 어떻게? 필요 없나?

        Sort sort =
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                        Sort.by(sortBy).ascending() :
                        Sort.by(sortBy).descending();

        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageRequest);

        log.info("posts = {}", posts);

        List<Post> postList = posts.getContent();
        log.info("postList = {}", postList);

        List<PostDto> content = postList.stream().map(Post::toDto).collect(Collectors.toList());
        log.info("postDtoList = {}", content);

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(content);
        postResponse.setPageNo(posts.getNumber());
        postResponse.setPageSize(posts.getSize());
        postResponse.setTotalElements(posts.getTotalElements());
        postResponse.setTotalPages(posts.getTotalPages());
        postResponse.setLast(posts.isLast());

        return postResponse;
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id+""));
        PostDto result = post.toDto();
        log.info("result = {}", result);
        return result;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {

        PostDto targetPost = getPostById(id);
        targetPost.setTitle(postDto.getTitle());
        targetPost.setDescription(postDto.getDescription());
        targetPost.setContent(postDto.getContent());
        PostDto result = createPost(targetPost);
        log.info("result = {}", result);

        return result;
    }


    @Override
    public PostDto deletePost(Long id) {

        PostDto findPost = getPostById(id);
        postRepository.deleteById(findPost.getPostId());
        return findPost;
    }

}
