package com.suhas.NotesApp.repository;

import com.suhas.NotesApp.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Integer> {
}
