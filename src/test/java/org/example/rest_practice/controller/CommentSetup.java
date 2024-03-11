package org.example.rest_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.entity.Comment;
import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.repository.CommentRepository;
import org.example.rest_practice.service.PostService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : org.example.rest_practice.controller
 * fileName       : CommentSetup
 * author         : AngryPig123
 * date           : 2024-03-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-11        AngryPig123       최초 생성
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public abstract class CommentSetup {


    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected PostService postService;

    @Autowired
    protected CommentRepository commentRepository;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    protected static final Long NOT_FOUND_ID = Long.MAX_VALUE - 2;

    protected String returnResourceNotFoundExceptionMessage(String resource, Long id) {
        return String.format("%s not found with %s : '%s'", resource, "id", id);
    }

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    protected PostDto postInsertHelper(PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        Assertions.assertNotNull(post);
        return post;
    }

    protected KeyMap commentCreateHelper(CommentDto commentDto, PostDto postDto) throws Exception {
        PostDto post = postInsertHelper(postDto);
        mockMvc.perform(
                        post("/api/v1/posts/{postId}/comments", post.getPostId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(commentDto.getName()))
                .andExpect(jsonPath("$.email").value(commentDto.getEmail()))
                .andExpect(jsonPath("$.body").value(commentDto.getBody()));
        Comment commentByEmail = commentRepository.findCommentByEmail(commentDto.getEmail());
        Assertions.assertNotNull(commentByEmail);
        Long commentId = commentByEmail.getCommentId();
        return new KeyMap(post.getPostId(), commentId);
    }


    protected static class KeyMap {
        private Long postId;
        private Long commentId;

        public KeyMap(Long postId, Long commentId) {
            this.postId = postId;
            this.commentId = commentId;
        }

        public Long getPostId() {
            return postId;
        }

        public void setPostId(Long postId) {
            this.postId = postId;
        }

        public Long getCommentId() {
            return commentId;
        }

        public void setCommentId(Long commentId) {
            this.commentId = commentId;
        }
    }

}
