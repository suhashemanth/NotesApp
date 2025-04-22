package com.suhas.NotesApp.dto;

import com.suhas.NotesApp.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterUserDto {
    private String name;
    private String password;
    private String email;
    private String phoneNumber;
    private UserRole role;
}
