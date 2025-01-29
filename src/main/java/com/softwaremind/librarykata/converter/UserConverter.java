package com.softwaremind.librarykata.converter;

import com.softwaremind.librarykata.controller.dto.UserDto;
import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.model.UserCredentials;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConverter {
    @Mapping(target = "userRole", source = "userCredentials.userRole")
    UserDto toDto(User user);
}
