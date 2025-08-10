package com.example.Commentator.controller;


import com.example.Commentator.dto.ArticleRequestDto;
import com.example.Commentator.dto.ArticleResponseDto;
import com.example.Commentator.service.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArticleResponseDto> create(@Valid @RequestBody ArticleRequestDto request) {
        ArticleResponseDto createdArticle = articleService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArticle);
    }

    @PutMapping
    public ArticleResponseDto update (@RequestParam Long articleId, @RequestBody ArticleRequestDto articleRequestDto) {
        return articleService.update(articleId, articleRequestDto);
    }

    @PutMapping("/{articleId}/like")
    public ResponseEntity<ArticleResponseDto> likeArticle(
            @PathVariable Long articleId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(articleService.likeArticle(articleId, userId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ArticleResponseDto>> getUserArticles(@PathVariable Long userId) {
        return ResponseEntity.ok(articleService.findAllArticlesByUser(userId));
    }

    @DeleteMapping("/{articleId}")
    public String deleteArticle (@PathVariable Long articleId) {
        articleService.delete(articleId);
        return "Article with " + articleId + " is deleted";
    }
}
