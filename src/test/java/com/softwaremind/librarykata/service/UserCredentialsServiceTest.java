package com.softwaremind.librarykata.service;

import com.softwaremind.librarykata.model.UserCredentials;
import com.softwaremind.librarykata.repository.UserCredentialsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCredentialsServiceTest {

    @Mock
    private UserCredentialsRepository userCredentialsRepository;

    @InjectMocks
    private UserCredentialsService userCredentialsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldSaveUserCredentials() {
        // Arrange
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setId(UUID.randomUUID());
        userCredentials.setUsername("johndoe");
        userCredentials.setPassword("password123");
        when(userCredentialsRepository.save(userCredentials)).thenReturn(userCredentials);

        // Act
        UserCredentials savedCredentials = userCredentialsService.save(userCredentials);

        // Assert
        assertNotNull(savedCredentials);
        assertEquals(userCredentials, savedCredentials);
        verify(userCredentialsRepository, times(1)).save(userCredentials);
    }

    @Test
    void findById_ShouldReturnUserCredentials_WhenIdExists() {
        // Arrange
        UUID id = UUID.randomUUID();
        UserCredentials userCredentials = new UserCredentials();
        userCredentials.setId(id);
        when(userCredentialsRepository.findById(id)).thenReturn(Optional.of(userCredentials));

        // Act
        Optional<UserCredentials> result = userCredentialsService.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userCredentials, result.get());
        verify(userCredentialsRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldReturnEmptyOptional_WhenIdDoesNotExist() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(userCredentialsRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        Optional<UserCredentials> result = userCredentialsService.findById(id);

        // Assert
        assertFalse(result.isPresent());
        verify(userCredentialsRepository, times(1)).findById(id);
    }

    @Test
    void findAll_ShouldReturnListOfUserCredentials() {
        // Arrange
        UserCredentials user1 = new UserCredentials();
        UserCredentials user2 = new UserCredentials();
        when(userCredentialsRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<UserCredentials> result = userCredentialsService.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
        verify(userCredentialsRepository, times(1)).findAll();
    }

    @Test
    void delete_ShouldDeleteUserCredentialsById() {
        // Arrange
        UUID id = UUID.randomUUID();
        doNothing().when(userCredentialsRepository).deleteById(id);

        // Act
        userCredentialsService.delete(id);

        // Assert
        verify(userCredentialsRepository, times(1)).deleteById(id);
    }
}
