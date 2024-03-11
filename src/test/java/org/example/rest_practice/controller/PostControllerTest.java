package org.example.rest_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.entity.Post;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.repository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : org.example.rest_practice.controller
 * fileName       : PostControllerTest
 * author         : AngryPig123
 * date           : 2024-03-10
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-03-10        AngryPig123       최초 생성
 */

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void beforeEach() {
        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    @Test
    @Order(1)
    void createPost() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.content").value("content"));
    }

    @Test
    @Order(2)
    void getAllPosts() throws Exception {

        //  주의 Pageable 에서 pageNo 는 0부터 시작.

        PostDto postDto = new PostDto(0L, "title", "description", "content");
        createHelper(postDto);
        mockMvc.perform(
                        get("/api/v1/posts?pageNo={pageNo}&pageSize={pageSize}&sortBy={sortBy}&sortDir={sortDir}", 0, 10, "title", "desc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true));

        postDto.setTitle("title2");
        createHelper(postDto);
        mockMvc.perform(
                        get("/api/v1/posts?pageNo={pageNo}&pageSize={pageSize}&sortBy={sortBy}&sortDir={sortDir}", 0, 10, "title", "desc")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNo").value(0))
                .andExpect(jsonPath("$.pageSize").value(10))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.last").value(true))
                .andDo(print());
    }

    @Test
    @Order(3)
    void getPostById() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        Long createId = createHelper(postDto);

        mockMvc.perform(
                        get("/api/v1/posts/{id}", createId)
                )
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/posts/{id}", Long.MAX_VALUE - 2)
                )
                .andExpect(status().isNotFound());

    }

    @Test
    @Order(4)
    void updatePost() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        Long createId = createHelper(postDto);
        PostDto updatePostDto = new PostDto(0L, "updateTitle", "updateDescription", "updateContent");

        mockMvc.perform(
                        put("/api/v1/posts/{id}", createId)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(updatePostDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(updatePostDto.getTitle()))
                .andExpect(jsonPath("$.description").value(updatePostDto.getDescription()))
                .andExpect(jsonPath("$.content").value(updatePostDto.getContent()));

        mockMvc.perform(
                        put("/api/v1/posts/{id}", Long.MAX_VALUE - 2)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(updatePostDto))
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(5)
    void deletePost() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        Long createId = createHelper(postDto);

        mockMvc.perform(
                        delete("/api/v1/posts/{id}", createId)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.description").value(postDto.getDescription()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));

        mockMvc.perform(
                        delete("/api/v1/posts/{id}", Long.MAX_VALUE - 2)
                )
                .andExpect(status().isNotFound());

    }

    private Long createHelper(PostDto postDto) throws Exception {
        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.description").value(postDto.getDescription()))
                .andExpect(jsonPath("$.content").value(postDto.getContent()));
        Post postByTitle = postRepository.findPostByTitle(postDto.getTitle());
        return postByTitle.getPostId();
    }

}
