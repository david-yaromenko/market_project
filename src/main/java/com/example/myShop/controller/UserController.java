package com.example.myShop.controller;

import com.example.myShop.dto.UserDTO;
import com.example.myShop.entity.User;
import com.example.myShop.exeption.UserAlreadyExistExeption;
import com.example.myShop.service.CustomUserDetailsManager;
import com.example.myShop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration.html";
    }

    @PostMapping("/registration")
    public String createUser(UserDTO userDTO, RedirectAttributes redirectAttributes){
        try {
            userService.createUser(userDTO);
            return "login.html";
        }catch (UserAlreadyExistExeption userAlreadyExistExeption) {
            redirectAttributes.addFlashAttribute("errorMessageExist", userAlreadyExistExeption.getMessage());
            return "redirect:/registration";
        }
    }
}
