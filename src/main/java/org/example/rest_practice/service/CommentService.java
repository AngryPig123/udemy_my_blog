package org.example.rest_practice.service;

import org.example.rest_practice.payload.CommentDto;

public interface CommentService {

    CommentDto createComment(Long postId, CommentDto commentDto);

    CommentDto getCommentsById(Long postId, Long commentId);
}