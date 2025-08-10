package com.example.Commentator.dto;

public record ArticleResponseDto(
        Long articleId,
        String title,
        String content,
        Long userId,
        String username
) {}

