package org.example.rest_practice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/posts")
public class CommentController {

    private final CommentService commentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.OK);
    }


    @GetMapping(path = "/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentsById(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return new ResponseEntity<>(commentService.getCommentsById(postId, commentId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(path = "/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId,
            @Valid @RequestBody CommentDto commentDto
    ) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteComment(
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId
    ) {
        return new ResponseEntity<>(commentService.deleteComment(postId, commentId), HttpStatus.OK);
    }

}
