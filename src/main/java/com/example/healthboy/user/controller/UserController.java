package com.example.healthboy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.user.dto.UserUpdateDto;
import com.example.healthboy.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProfile(@PathVariable Long id,
            @Valid @RequestBody UserUpdateDto userUpdateDto) {
        return userService.updateProfile(id, userUpdateDto);
    }
}
