package com.suhas.NotesApp.controller;

import com.suhas.NotesApp.model.Student;
import com.suhas.NotesApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents()
    {
        List<Student> allStudents = studentService.getAllStudents();
        return ResponseEntity.ok(allStudents);
    }

    @PostMapping
    public ResponseEntity<Student> registerStudent(@RequestBody Student student)
    {
        Student allStudents = studentService.addStudent(student);
        return ResponseEntity.ok(allStudents);
    }

}
