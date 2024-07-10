package com.example.testtask.service;

import com.example.testtask.entity.Student;
import com.example.testtask.repository.StudentRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataInitializationService {

    private final StudentRepository studentRepository;

    public DataInitializationService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PostConstruct
    public void initData() {
        final int NUM_STUDENTS = 20;
        List<Student> students = new ArrayList<>();
        for (int i = 1; i <= NUM_STUDENTS; i++) {
            Student student = new Student("Student" + i, i);
            students.add(student);
        }

        studentRepository.saveAll(students);
    }
}