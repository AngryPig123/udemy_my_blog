package org.example.rest_practice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.rest_practice.payload.PostDto;
import org.example.rest_practice.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

@SpringBootTest
@AutoConfigureMockMvc
class PostControllerTest {


//    @RequestMapping(path = "/api/v1/posts")
//    public class PostController {
//
//        private final PostService postService;
//
//        @PostMapping
//        public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//            return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.OK);
//        }
//
//    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createPost() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        mockMvc.perform(
                        post("/api/v1/posts")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(postDto))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("title"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.content").value("content"));

    }

    @Test
    void getAllPosts() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content");
        mockMvc.perform(
                        get("/api/v1/posts")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(0));

        createPost();

        mockMvc.perform(
                        get("/api/v1/posts")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));

    }

}