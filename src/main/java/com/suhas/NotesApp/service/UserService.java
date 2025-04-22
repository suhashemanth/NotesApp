package com.suhas.NotesApp.service;

import com.suhas.NotesApp.model.User;
import com.suhas.NotesApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    public User registerUser(User user)
    {
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        return userRepository.save(user);
    }

    public String verify(User user)
    {
        Authentication authentication=authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword())
        );
        if(authentication.isAuthenticated())
        {
            return jwtService.generateToken(user.getEmail());
        }
        return "Failed";
    }
}
