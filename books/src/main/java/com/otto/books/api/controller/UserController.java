package com.otto.books.api.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import com.otto.books.api.dto.UserDTO;
import com.otto.books.model.entity.User;
import com.otto.books.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * UserController
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    @PostMapping("auth")
    public ResponseEntity auth(@RequestBody UserDTO dto) {
        try {
            User userAuth = this.service.authenticate(dto.getEmail(), dto.getPassword());
            return ResponseEntity.ok(userAuth);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody UserDTO dto) {
        User user = User.builder().username(dto.getUsername()).password(dto.getPassword()).email(dto.getEmail())
                .build();
        try {
            User saveUser = this.service.createUser(user);
            return new ResponseEntity(saveUser, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable("id") Long id, @RequestBody UserDTO dto) {

        return this.service.getById(id).map(entity -> {
            try {
                User user = User.builder().username(dto.getUsername()).password(dto.getPassword()).email(dto.getEmail())
                        .build();
                user.setId(id);
                user = this.service.createUser(user);
                return ResponseEntity.ok(user);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> new ResponseEntity("UserBooks Not Found", HttpStatus.BAD_REQUEST));
    }

    @GetMapping
    public ResponseEntity<List<User>> getALL() {
        List<User> users = null;
        try {
            users = this.service.getAllUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathParam("id") Long id) {
        Optional<User> userOptional = this.service.getById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok(userOptional);
    }

}