package com.example.Commentator.mapper;

import com.example.Commentator.dto.CommentRequestDto;
import com.example.Commentator.dto.CommentResponseDto;
import com.example.Commentator.entity.Article;
import com.example.Commentator.entity.Comment;
import com.example.Commentator.entity.User;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface CommentMapper {

    @Mapping(target = "commentId", source = "id")
    @Mapping(target = "articleId", source = "article.id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "content", source = "content")
    @Mapping(target = "replies", ignore = true)
    CommentResponseDto toResponseDto(Comment comment);


    List<CommentResponseDto> toResponseDtoList(List<Comment> comments);
}


