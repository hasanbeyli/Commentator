package com.example.Commentator.mapper;


import com.example.Commentator.dto.UserResponseDto;
import com.example.Commentator.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    @Mapping(target = "userId", source = "id")
    UserResponseDto toUserResponseDto(User user);

    List<UserResponseDto> toUserResponseDtoList(List<User> users);

}
