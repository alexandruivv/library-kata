package com.softwaremind.librarykata.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwaremind.librarykata.controller.dto.UserDto;
import com.softwaremind.librarykata.controller.requests.LoginRequest;
import com.softwaremind.librarykata.facade.UserFacade;
import com.softwaremind.librarykata.model.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnUserDtoOnSuccessfulLogin() throws Exception {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        UserDto userDto = new UserDto(UUID.randomUUID(), "firstname", "lastname", UserRole.ADMIN.name());

        when(userFacade.loginUser(loginRequest)).thenReturn(userDto);

        // Act & Assert
        mockMvc.perform(post("/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("firstname"))
                .andExpect(jsonPath("$.lastName").value("lastname"))
                .andExpect(jsonPath("$.userRole").value("ADMIN"));
    }
}
