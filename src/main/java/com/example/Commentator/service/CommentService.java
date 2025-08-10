package com.example.Commentator.service;


import com.example.Commentator.dto.CommentRequestDto;
import com.example.Commentator.dto.CommentResponseDto;
import com.example.Commentator.entity.Article;
import com.example.Commentator.entity.Comment;
import com.example.Commentator.entity.User;
import com.example.Commentator.mapper.CommentMapper;
import com.example.Commentator.repository.ArticleRepo;
import com.example.Commentator.repository.CommentRepo;
import com.example.Commentator.repository.UserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepo commentRepo;
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;
    private final CommentMapper commentMapper;

    @Transactional
    public CommentResponseDto addComment(CommentRequestDto req) {

        User user = userRepo.findById(req.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Article article = articleRepo.findById(req.articleId())
                .orElseThrow(() -> new RuntimeException("Article not found"));

        Comment commentForReply = null;
        if (req.commentId() != null) {
            commentForReply = commentRepo.findById(req.commentId())
                    .orElseThrow(() -> new RuntimeException("Reply comment not found"));
        }

        Comment comment = new Comment();
        comment.setArticle(article);
        comment.setUser(user);
        comment.setComment(commentForReply);
        comment.setContent(req.comment());
        Comment saved = commentRepo.save(comment);
        return commentMapper.toResponseDto(saved);
    }

    public List<CommentResponseDto> getArticleComments(Long articleId) {
        Article article = articleRepo.findById(articleId)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        List<Comment> commentList = commentRepo.findByArticle(article);
        return commentMapper.toResponseDtoList(commentList);
    }

    @Transactional
    public CommentResponseDto likeComment(Long commentId, Long userId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (comment.getLikedBy().contains(user)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already liked this comment");
        }

        comment.getLikedBy().add(user);

        Comment saved = commentRepo.save(comment);
        return commentMapper.toResponseDto(saved);
    }

    @Transactional
    public CommentResponseDto update(CommentRequestDto commentRequestDto) {
        Comment comment = commentRepo.findById(commentRequestDto.commentId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getArticle().getId().equals(commentRequestDto.articleId())) {
            throw new RuntimeException("Comment does not belong to this article");
        }

        comment.setContent(commentRequestDto.comment());
        Comment saved = commentRepo.save(comment);  
        return commentMapper.toResponseDto(saved);
    }

    @Transactional
    public void deleteComment(Long articleId, Long commentId) {
        boolean exists = getArticleComments(articleId).stream()
                .anyMatch(comment -> comment.commentId().equals(commentId));

        if (!exists) {
            throw new RuntimeException("Comment not found for this article");
        }
        commentRepo.deleteById(commentId);
    }

}
