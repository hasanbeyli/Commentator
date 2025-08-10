package com.example.Commentator.repository;

import com.example.Commentator.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepo extends JpaRepository<Article, Long> {

    List<Article> findByUserId(Long userId);

}
