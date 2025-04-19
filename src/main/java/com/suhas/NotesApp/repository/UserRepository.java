package com.suhas.NotesApp.repository;

import com.suhas.NotesApp.enums.UserRoles;
import com.suhas.NotesApp.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Users,Long> {

    Optional<Users> findByEmail(String email);

    List<Users> findByUserRole(UserRoles role);

    void deleteByEmail(String email);
}
