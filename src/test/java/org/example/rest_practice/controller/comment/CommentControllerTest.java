package org.example.rest_practice.controller.comment;

import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.payload.PostDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@WithMockUser(username = "admin", password = "admin", roles = {"ADMIN"})
class CommentControllerTest extends CommentSetup {


    @Test
    void createComments() throws Exception {
        PostDto postDto = new PostDto(0L, "title", "description", "content", new HashSet<>());
        CommentDto commentDto = new CommentDto(0L, "name", "email@gmail.com", "body body body body");
        commentCreateHelper(commentDto, postDto);

        mockMvc.perform(
                        post("/api/v1/posts/{id}/comments", NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentDto))
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Post", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());
    }

    @Test
    void getCommentsById() throws Exception {

        PostDto postDto = new PostDto(0L, "title", "description", "content", new HashSet<>());
        CommentDto commentDto = new CommentDto(0L, "name", "email@gmail.com", "body body body body");
        KeyMap keyMap = commentCreateHelper(commentDto, postDto);

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), keyMap.getCommentId())
                )
                .andExpect(jsonPath("$.name").value(commentDto.getName()))
                .andExpect(jsonPath("$.email").value(commentDto.getEmail()))
                .andExpect(jsonPath("$.body").value(commentDto.getBody()))
                .andExpect(status().isOk());

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", NOT_FOUND_ID, keyMap.getCommentId())
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Post", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        get("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), NOT_FOUND_ID)
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Comment", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

    }


    @Test
    void updateComment() throws Exception {

        PostDto postDto = new PostDto(0L, "title", "description", "content", new HashSet<>());
        CommentDto commentDto = new CommentDto(0L, "name", "email@gmail.com", "body body body body");
        KeyMap keyMap = commentCreateHelper(commentDto, postDto);

        CommentDto updateCommentDto = new CommentDto(0L, "updateName", "email@gmail.com", "update body body body body");

        mockMvc.perform(
                        put("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), keyMap.getCommentId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateCommentDto))
                )
                .andDo(print())
                .andExpect(jsonPath("$.name").value(updateCommentDto.getName()))
                .andExpect(jsonPath("$.email").value(updateCommentDto.getEmail()))
                .andExpect(jsonPath("$.body").value(updateCommentDto.getBody()))
                .andExpect(status().isOk());

        mockMvc.perform(
                        put("/api/v1/posts/{postId}/comments/{commentId}", NOT_FOUND_ID, keyMap.getCommentId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateCommentDto))
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Post", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        put("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), NOT_FOUND_ID)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updateCommentDto))
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Comment", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteComment() throws Exception {

        PostDto postDto = new PostDto(0L, "title", "description", "content", new HashSet<>());
        CommentDto commentDto = new CommentDto(0L, "name", "email@gmail.com", "body body body body");
        KeyMap keyMap = commentCreateHelper(commentDto, postDto);

        mockMvc.perform(
                        delete("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), keyMap.getCommentId())
                )
                .andExpect(jsonPath("$.name").value(commentDto.getName()))
                .andExpect(jsonPath("$.email").value(commentDto.getEmail()))
                .andExpect(jsonPath("$.body").value(commentDto.getBody()))
                .andExpect(status().isOk());

        mockMvc.perform(
                        delete("/api/v1/posts/{postId}/comments/{commentId}", NOT_FOUND_ID, keyMap.getCommentId())
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Post", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

        mockMvc.perform(
                        delete("/api/v1/posts/{postId}/comments/{commentId}", keyMap.getPostId(), NOT_FOUND_ID)
                )
                .andExpect(jsonPath("$.message").value(returnResourceNotFoundExceptionMessage("Comment", NOT_FOUND_ID)))
                .andExpect(status().isNotFound());

    }

}
