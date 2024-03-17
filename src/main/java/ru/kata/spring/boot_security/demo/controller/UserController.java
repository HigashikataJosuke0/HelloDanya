package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceFind;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceFind userServiceFind;

    @Autowired
    public UserController(UserServiceFind userServiceFind) {
        this.userServiceFind = userServiceFind;
    }

    @GetMapping
    public User userInfo(Principal principal) {
        return userServiceFind.findByUsername(principal.getName()).get();
    }

}
