package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceFind;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserServiceFind userServiceFind;
    private final RoleService roleService;
    private final UserRepository userRepository;

    @Autowired
    public AdminController(UserServiceFind userServiceFind, RoleService roleService, UserRepository userRepository) {
        this.userServiceFind = userServiceFind;
        this.roleService = roleService;
        this.userRepository = userRepository;
    }


    @GetMapping
    public List<User> indexView() {
        return userServiceFind.allUsers();
    }

    @PostMapping("/addnewuser")
    public ResponseEntity<HttpStatus> addUserView(@RequestBody @Valid User user, BindingResult bindingResult) {
        userServiceFind.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/saveUser/{id}")
    public ResponseEntity<HttpStatus> add(@PathVariable("id") Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
        user.setId(id);
        userServiceFind.save(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
        userServiceFind.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return userServiceFind.findUserById(id);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

}
