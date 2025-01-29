package com.softwaremind.librarykata.controller;

import com.softwaremind.librarykata.controller.dto.UserDto;
import com.softwaremind.librarykata.controller.requests.LoginRequest;
import com.softwaremind.librarykata.facade.UserFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserFacade userFacade;

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userFacade.loginUser(loginRequest));
    }
}
