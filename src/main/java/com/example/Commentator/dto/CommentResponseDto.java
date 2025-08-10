package com.example.Commentator.dto;
import java.util.List;

public record CommentResponseDto(
        Long articleId,
        Long commentId,
        Long userId,
        String username,
        String content,
        List<CommentResponseDto> replies
) {
}

