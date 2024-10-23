package org.example.finman.service.user;

import org.example.finman.dto.user.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto createUser(UserDto userDto);

    void deleteUser(Long id);

    UserDto updateUser(Long id, UserDto userDto);

    void increaseCredit(long id, double amount);
}
