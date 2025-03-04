package com.example.myShop.service;

import com.example.myShop.dto.UserDTO;
import com.example.myShop.entity.User;
import com.example.myShop.exeption.UserAlreadyExistExeption;
import com.example.myShop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final MapperService mapperService;

    public boolean createUser(UserDTO userDTO){

        Optional<User> userDb = userRepository.findByEmail(userDTO.email());
        if (userDb.isPresent()){
            System.out.println(userDTO.email() + " already exist");
            throw new UserAlreadyExistExeption("User with this email already exist!");
        }
        var entity = mapperService.userToEntity(userDTO);
        entity.setRole("USER");
        var user = userRepository.save(entity);
        mapperService.userToDTO(user);
        return true;
    }
}
