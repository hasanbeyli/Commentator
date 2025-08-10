package com.example.Commentator.controller;

import com.example.Commentator.dto.ArticleResponseDto;
import com.example.Commentator.dto.CommentRequestDto;
import com.example.Commentator.dto.CommentResponseDto;
import com.example.Commentator.entity.Comment;
import com.example.Commentator.service.CommentService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @RateLimiter(name = "commentLimiter")
    @PostMapping("/{articleId}")
    public ResponseEntity<CommentResponseDto> create(
            @RequestBody CommentRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.addComment(request));
    }

    @PutMapping("/{commentId}/like")
    public ResponseEntity<CommentResponseDto> likeComment(
            @PathVariable Long commentId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(commentService.likeComment(commentId, userId));
    }

    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentResponseDto>> list(@PathVariable Long articleId) {
        return ResponseEntity.ok(commentService.getArticleComments(articleId));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> update(@RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok(commentService.update(commentRequestDto));
    }

    @DeleteMapping("/{commentId}")
    public void delete(@RequestParam Long articleId, @PathVariable Long commentId) {
        commentService.deleteComment(articleId, commentId);
    }
}

