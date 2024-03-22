package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserServiceFind;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserServiceFind userServiceFind;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserServiceFind userServiceFind, RoleService roleService) {
        this.userServiceFind = userServiceFind;
        this.roleService = roleService;
    }



    @GetMapping
    public List<User> indexView() {
        return userServiceFind.allUsers();
    }

    @PostMapping("/addnewuser")
    public ResponseEntity<?> addUserView(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errors", bindingResult.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        userServiceFind.save(user);
        Map<String,String> response = new HashMap<>();
        response.put("massage","User was add");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/saveUser/{id}")
    public ResponseEntity<?> add(@PathVariable("id") Long id, @RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, Object> response = new HashMap<>();
            response.put("errors", bindingResult.getAllErrors());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        user.setId(id);
        userServiceFind.save(user);
        Map<String,String> response = new HashMap<>();
        response.put("message", "User was update");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userServiceFind.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "User was removed");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        User user = userServiceFind.findUserById(id);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/roles")
    public List<Role> getAllRoles() {
        return roleService.findAll();
    }

}
