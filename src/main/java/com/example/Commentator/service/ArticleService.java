package com.example.Commentator.service;


import com.example.Commentator.dto.ArticleRequestDto;
import com.example.Commentator.dto.ArticleResponseDto;
import com.example.Commentator.entity.Article;
import com.example.Commentator.entity.User;
import com.example.Commentator.mapper.ArticleMapper;
import com.example.Commentator.repository.ArticleRepo;
import com.example.Commentator.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;
    private final ArticleMapper articleMapper;

    @Transactional
    public ArticleResponseDto create(ArticleRequestDto req) {
        User user = userRepo.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User doesn't exist, create user first"));

        Article article = Article.builder()
                .title(req.title())
                .content(req.content())
                .user(user)
                .build();

        Article saved = articleRepo.save(article);

        return new ArticleResponseDto(
                saved.getId(), saved.getTitle(), saved.getContent(),
                user.getId(), user.getUsername()
        );
    }

    @Transactional
    public ArticleResponseDto likeArticle(Long articleId, Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleRepo.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        if (!article.getLikedBy().add(user)) {
            throw new IllegalStateException("User already liked this article");
        }

        Article saved = articleRepo.save(article);

        return articleMapper.toArticleResponseDto(saved);
    }

    public List<ArticleResponseDto> findAllArticlesByUser(Long userId) {
        List<Article> articles = articleRepo.findByUserId(userId);
        return articleMapper.toArticleResponseDtoList(articles);
    }

    public ArticleResponseDto update(Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = articleRepo.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
        article.setContent(articleRequestDto.content());
        article.setTitle(articleRequestDto.title());
        article = articleRepo.save(article);
        return articleMapper.toArticleResponseDto(article);
    }

    public void delete(Long articleId) {
        Article article = articleRepo.findById(articleId).orElseThrow(() -> new RuntimeException("Article not found"));
        articleRepo.delete(article);
    }
}
