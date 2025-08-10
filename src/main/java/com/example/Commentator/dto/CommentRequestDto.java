package com.example.Commentator.dto;

public record CommentRequestDto(Long articleId, Long commentId, Long userId, String comment) {

}
