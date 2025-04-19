package com.suhas.NotesApp.security.service;

import com.suhas.NotesApp.enums.UserRoles;
import com.suhas.NotesApp.model.Users;
import com.suhas.NotesApp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Users> getUsersWithRoles(UserRoles roles) {
        return userRepository.findByUserRole(roles);
    }

    @Override
    public Users getAUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("UserNot found"));

    }

    @Override
    public Users AddAUser(Users users) {
        return userRepository.save(users);
    }

    @Override
    public Users updateAUser(String email, Users users) {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setName(users.getName());
        user.setPassword(users.getPassword());
        user.setUserRole(users.getUserRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteAUser(String email) {
        userRepository.deleteByEmail(email);
    }
}
