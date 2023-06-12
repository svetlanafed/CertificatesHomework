package com.example.certificateshomework.mapper;

import com.example.certificateshomework.dto.UserDto;
import com.example.certificateshomework.entity.UserEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto map(UserEntity entity);

    @InheritInverseConfiguration
    UserEntity map(UserDto dto);
}
