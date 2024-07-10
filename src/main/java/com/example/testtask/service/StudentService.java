package com.example.testtask.service;


import com.example.testtask.entity.Student;
import com.example.testtask.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Page<Student> getAllStudents(int page, int size) {
        return studentRepository.findAllByOrderByOrder(PageRequest.of(page, size));
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        int order = student.getOrder();
        studentRepository.delete(student);

        List<Student> studentsToShift = studentRepository.findAllByOrderGreaterThanOrderByOrder(order);
        for (Student s : studentsToShift) {
            s.setOrder(s.getOrder() - 1);
            studentRepository.save(s);
        }
    }

    public void updateStudent(Long id, Student studentDetails) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        student.setName(studentDetails.getName());
        studentRepository.save(student);
    }

    @Transactional
    public void moveStudentUp(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        Student previousStudent = studentRepository.findByOrder(student.getOrder() - 1);
        if (previousStudent != null) {
            int currentOrder = student.getOrder();
            student.setOrder(previousStudent.getOrder());
            previousStudent.setOrder(currentOrder);
            studentRepository.save(student);
            studentRepository.save(previousStudent);
        }
    }

    @Transactional
    public void moveStudentDown(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid student Id:" + id));
        Student nextStudent = studentRepository.findByOrder(student.getOrder() + 1);
        if (nextStudent != null) {
            int currentOrder = student.getOrder();
            student.setOrder(nextStudent.getOrder());
            nextStudent.setOrder(currentOrder);
            studentRepository.save(student);
            studentRepository.save(nextStudent);
        }
    }
}
