package org.example.rest_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.entity.Comment;
import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.repository.CommentRepository;
import org.example.rest_practice.service.PostService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    void createComments() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        CommentDto commentDto = new CommentDto(0L, "name", "email", "body");
        commentCreateHelper(commentDto, postDto);

        mockMvc.perform(
                        post("/api/v1/posts/{id}/comments", Long.MAX_VALUE - 2)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentDto))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    void getCommentsById() throws Exception {

        PostDto postDto = new PostDto(0L, "title", "description", "content");
        CommentDto commentDto = new CommentDto(0L, "name", "email", "body");
        KeyMap keyMap = commentCreateHelper(commentDto, postDto);

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), keyMap.getCommentId())
                )
                .andExpect(jsonPath("$.name").value(commentDto.getName()))
                .andExpect(jsonPath("$.email").value(commentDto.getEmail()))
                .andExpect(jsonPath("$.body").value(commentDto.getBody()))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", Long.MAX_VALUE - 2, keyMap.getCommentId())
                )
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), Long.MAX_VALUE - 2)
                )
                .andExpect(status().isNotFound());

    }

    private PostDto postInsertHelper(PostDto postDto) {
        PostDto post = postService.createPost(postDto);
        Assertions.assertNotNull(post);
        return post;
    }

    private KeyMap commentCreateHelper(CommentDto commentDto, PostDto postDto) throws Exception {
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


    private static class KeyMap {
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