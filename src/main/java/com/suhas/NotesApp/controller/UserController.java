package com.suhas.NotesApp.controller;

import com.suhas.NotesApp.dto.RegisterUserDto;
import com.suhas.NotesApp.model.User;
import com.suhas.NotesApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody RegisterUserDto userDto)
    {
        User user=new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setRole(userDto.getRole());
        User user1 = userService.registerUser(user);
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user)
    {
        System.out.println(user);
        if(userService.verify(user).equals("Failed"))
        {
            return new ResponseEntity<>("Failed to login",HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(userService.verify(user));
    }
}
