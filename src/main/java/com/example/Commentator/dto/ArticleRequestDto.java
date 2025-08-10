package com.example.Commentator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ArticleRequestDto(
        @NotNull Long userId,
        @NotBlank String title,
        String content) {
}
