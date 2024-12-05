package com.youcode.product_managementV2.mapper;

import com.youcode.product_managementV2.Entity.User;
import com.youcode.product_managementV2.dto.request.UserRequestDto;
import com.youcode.product_managementV2.dto.response.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    User toEntity(UserRequestDto userRequestDto);
    UserResponseDto toResponseDto(User user);
}
