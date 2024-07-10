package com.example.testtask.repository;



import com.example.testtask.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findAllByOrderByOrder(Pageable pageable);
    Student findByOrder(int order);
    List<Student> findAllByOrderGreaterThanOrderByOrder(int order);
}

