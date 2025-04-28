package com.example.myShop;

import com.example.myShop.dto.UserDTO;
import com.example.myShop.entity.User;
import com.example.myShop.exeption.UserAlreadyExistExeption;
import com.example.myShop.repository.UserRepository;
import com.example.myShop.service.MapperService;
import com.example.myShop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MapperService mapperService;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void start(){

        userDTO = new UserDTO(null, "asd@gmail.com", "123");

        user = new User();
        user.setId(1);
        user.setEmail("asd@gmail.com");
        user.setPassword("123");
        user.setRole("USER");

    }

    @Test
    void createUserTest(){

        var userWrongDTO = new UserDTO(null, "asd2@gmail.com", "123");

        var userWrong = new User();
        userWrong.setId(2);
        userWrong.setEmail("asd2@gmail.com");
        userWrong.setPassword("123");
        userWrong.setRole(null);

        when(mapperService.userToEntity(userDTO)).thenReturn(user);
        when(mapperService.userToEntity(userWrongDTO)).thenReturn(userWrong);

        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.save(userWrong)).thenReturn(userWrong);

        when(mapperService.userToDTO(user)).thenReturn(userDTO);
        when(mapperService.userToDTO(userWrong)).thenReturn(userWrongDTO);

        UserDTO saveUser = userService.createUser(userDTO);
        UserDTO saveUser2 = userService.createUser(userWrongDTO);

        verify(userRepository).save(eq(user));
        verify(userRepository).save(eq(userWrong));

        assertEquals("asd@gmail.com", saveUser.email());
        assertEquals("asd2@gmail.com", saveUser2.email());
        assertEquals("USER", user.getRole());
        assertEquals("USER", userWrong.getRole());
    }

    @Test
    void createUser_WhenUserAlreadyExist(){

        UserDTO invalidUser = new UserDTO(null, "asd@gmail.com", "123");

        User userExist = new User();
        userExist.setId(1);
        userExist.setEmail("asd@gmail.com");
        userExist.setPassword("password");

        when(userRepository.findByEmail("asd@gmail.com")).thenReturn(Optional.of(userExist));

        Exception exception = assertThrows(UserAlreadyExistExeption.class, () -> {
            userService.createUser(invalidUser);
        });

        assertEquals("User with this email already exist!", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));

    }
}




















