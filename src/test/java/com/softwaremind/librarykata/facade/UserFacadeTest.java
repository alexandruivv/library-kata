package com.softwaremind.librarykata.facade;

import com.softwaremind.librarykata.controller.dto.UserDto;
import com.softwaremind.librarykata.controller.requests.LoginRequest;
import com.softwaremind.librarykata.converter.UserConverter;
import com.softwaremind.librarykata.exception.ErrorCodes;
import com.softwaremind.librarykata.exception.LibraryKataResponseException;
import com.softwaremind.librarykata.model.User;
import com.softwaremind.librarykata.model.UserRole;
import com.softwaremind.librarykata.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private UserConverter userConverter;

    @InjectMocks
    private UserFacade userFacade;

    public UserFacadeTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnUserDtoOnSuccessfulLogin() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("username", "password");
        User user = new User(); // Assume User has default values or setup user object
        UserDto userDto = new UserDto(UUID.randomUUID(), "firstname", "lastname", UserRole.ADMIN.name());

        when(userService.findByUserCredentials("username", "password")).thenReturn(Optional.of(user));
        when(userConverter.toDto(user)).thenReturn(userDto);

        // Act
        UserDto result = userFacade.loginUser(loginRequest);

        // Assert
        assertNotNull(result);
        assertEquals("firstname", result.getFirstName());
        assertEquals("lastname", result.getLastName());
        assertEquals("ADMIN", result.getUserRole());

        // Verify that the dependencies were called correctly
        verify(userService).findByUserCredentials("username", "password");
        verify(userConverter).toDto(user);
    }

    @Test
    void shouldThrowExceptionWhenInvalidCredentials() {
        // Arrange
        LoginRequest loginRequest = new LoginRequest("invalidUser", "invalidPass");

        when(userService.findByUserCredentials("invalidUser", "invalidPass")).thenReturn(Optional.empty());

        // Act & Assert
        LibraryKataResponseException exception = assertThrows(LibraryKataResponseException.class, () -> {
            userFacade.loginUser(loginRequest);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getHttpStatus());
        assertEquals(ErrorCodes.INVALID_CREDENTIALS, exception.getErrorCode());

        // Verify that the service was called and the converter was not called
        verify(userService).findByUserCredentials("invalidUser", "invalidPass");
        verifyNoInteractions(userConverter);
    }
}
