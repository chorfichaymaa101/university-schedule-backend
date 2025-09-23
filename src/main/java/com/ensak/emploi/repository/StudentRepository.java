package com.ensak.emploi.repository;

import com.ensak.emploi.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
}

