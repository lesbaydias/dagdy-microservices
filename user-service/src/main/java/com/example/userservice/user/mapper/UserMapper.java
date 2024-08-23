package com.example.userservice.user.mapper;

import com.example.userservice.auth.dto.RegisterRequest;
import com.example.userservice.user.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    Users registerRequestToUsers(RegisterRequest registerRequest);
}
