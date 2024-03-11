package org.example.rest_practice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.rest_practice.entity.Comment;
import org.example.rest_practice.entity.Post;
import org.example.rest_practice.exception.BlogApiException;
import org.example.rest_practice.exception.ResourceNotFoundException;
import org.example.rest_practice.payload.CommentDto;
import org.example.rest_practice.repository.CommentRepository;
import org.example.rest_practice.repository.PostRepository;
import org.example.rest_practice.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;


    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment = commentDto.toEntity();
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        comment.setPostOnComments(post);
        Comment saveComment = commentRepository.save(comment);
        CommentDto result = saveComment.toDto();
        log.info("result = {}", result);
        return result;
    }

    @Override
    public CommentDto getCommentsById(Long postId, Long commentId) {

        Comment comment = resutnComment(postId, commentId);

        CommentDto result = comment.toDto();
        log.info("result = {}", result);

        return result;
    }


    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Comment findComment = resutnComment(postId, commentId);
        findComment.setName(commentDto.getName());
        findComment.setEmail(commentDto.getEmail());
        findComment.setBody(commentDto.getBody());
        Comment saveComment = commentRepository.save(findComment);
        CommentDto result = saveComment.toDto();
        log.info("result = {}", result);
        return result;
    }

    @Override
    public CommentDto deleteComment(Long postId, Long commentId) {
        Comment comment = resutnComment(postId, commentId);
        CommentDto result = comment.toDto();
        commentRepository.delete(comment);
        log.info("result = {}", result);
        return result;
    }

    private Comment resutnComment(Long postId, Long commentId) {

        Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));

        if (!comment.getPost().getPostId().equals(post.getPostId())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Comment does not belong to post");
        }
        return comment;
    }

}
