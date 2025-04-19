package com.suhas.NotesApp.security.service;

import com.suhas.NotesApp.dtos.UsersDto;
import com.suhas.NotesApp.enums.UserRoles;
import com.suhas.NotesApp.model.Users;

import java.util.List;

public interface UserService {

     List<Users> getUsers();
     List<Users> getUsersWithRoles(UserRoles roles);
     Users getAUser(String email);
     Users AddAUser(Users users);
     Users updateAUser(String email,Users users);
     void deleteAUser(String email);
}
