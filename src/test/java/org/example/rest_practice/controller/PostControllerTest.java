package org.example.rest_practice.controller;

import org.example.rest_practice.payload.PostDto;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
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
class PostControllerTest extends PostSetup {

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
                        get("/api/v1/posts/{id}", NOT_FOUND_ID)
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage(NOT_FOUND_ID)))
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
                        put("/api/v1/posts/{id}", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(updatePostDto))
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage(NOT_FOUND_ID)))
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
                        delete("/api/v1/posts/{id}", NOT_FOUND_ID)
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage(NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

    }

}
