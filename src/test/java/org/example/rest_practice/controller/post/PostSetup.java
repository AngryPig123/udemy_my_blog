package org.example.rest_practice.controller.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.entity.Post;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * packageName    : org.example.rest_practice.controller
 * fileName       : PostSetup
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
public abstract class PostSetup {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected JdbcTemplate jdbcTemplate;

    @Autowired
    protected PostRepository postRepository;

    protected static final Long NOT_FOUND_ID = Long.MAX_VALUE - 2;

    @BeforeEach
    void beforeEach(WebApplicationContext wac) {

        mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity()).build();

        jdbcTemplate.execute("DELETE FROM posts");
        jdbcTemplate.execute("DELETE FROM comments");
    }

    protected String returnResourceNotFoundExceptionMessage(Long id) {
        return String.format("%s not found with %s : '%s'", "Post", "id", id);
    }


    protected Long createHelper(PostDto postDto) throws Exception {
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
