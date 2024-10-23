package org.example.finman.service.user;

import org.example.finman.domain.user.User;
import org.example.finman.dto.user.UserDto;
import org.example.finman.repository.user.UserRepository;
import org.example.finman.service.user.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> users = Arrays.asList(
                new User(1L, "user1", "password1"),
                new User(2L, "user2", "password2")
        );
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDto> result = userService.getAllUsers();

        // Assert
        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
    }

    @Test
    public void testGetUserById() {
        // Arrange
        User user = new User(1L, "user1", "password1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        UserDto result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    public void testCreateUser() {
        // Arrange
        UserDto userDto = new UserDto();
        userDto.setUsername("user1");
        userDto.setPassword("password1");

        User user = new User();
        user.setUsername("user1");
        user.setPassword("password1");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDto result = userService.createUser(userDto);

        // Assert
        assertNotNull(result);
        assertEquals("user1", result.getUsername());
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User existingUser = new User(1L, "user1", "password1");
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        UserDto userDto = new UserDto();
        userDto.setUsername("updatedUser");
        userDto.setPassword("updatedPassword");

        // Act
        UserDto result = userService.updateUser(1L, userDto);

        // Assert
        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
    }

    @Test
    public void testDeleteUser() {
        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}