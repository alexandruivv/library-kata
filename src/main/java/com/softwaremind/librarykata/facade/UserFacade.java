package com.softwaremind.librarykata.facade;

import com.softwaremind.librarykata.controller.dto.UserDto;
import com.softwaremind.librarykata.controller.requests.LoginRequest;
import com.softwaremind.librarykata.converter.UserConverter;
import com.softwaremind.librarykata.exception.ErrorCodes;
import com.softwaremind.librarykata.exception.LibraryKataResponseException;
import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserConverter userConverter;

    public UserDto loginUser(LoginRequest loginRequest) {
        User user = userService.findByUserCredentials(loginRequest.getUsername(), loginRequest.getPassword())
                .orElseThrow(() -> new LibraryKataResponseException(HttpStatus.BAD_REQUEST, ErrorCodes.INVALID_CREDENTIALS));
        return userConverter.toDto(user);
    }
}
