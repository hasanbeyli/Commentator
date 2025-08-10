package com.example.Commentator.repository;

import com.example.Commentator.entity.Article;
import com.example.Commentator.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findByArticle(Article article);



}
