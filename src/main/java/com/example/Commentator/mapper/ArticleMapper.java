package com.example.Commentator.mapper;

import com.example.Commentator.dto.ArticleResponseDto;
import com.example.Commentator.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface ArticleMapper {

    @Mapping(target = "articleId", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "username", source = "user.username")
    ArticleResponseDto toArticleResponseDto(Article article);

    List<ArticleResponseDto> toArticleResponseDtoList(List<Article> articles);

}

