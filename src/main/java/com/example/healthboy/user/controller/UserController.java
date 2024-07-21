package com.example.healthboy.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.healthboy.user.dto.CreateUserDto;
import com.example.healthboy.user.dto.UserDto;
import com.example.healthboy.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<UserDto> createOrUpdateUser(@RequestBody CreateUserDto createUserDto) {
        UserDto userDto = userService.createOrUpdateUser(createUserDto);
        return ResponseEntity.ok(userDto);
    }
}
