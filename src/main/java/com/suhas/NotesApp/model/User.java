package com.suhas.NotesApp.model;

import com.suhas.NotesApp.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String password;
    @Column(unique = true)
    private String email;
    @Column(name="phone_number")
    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
