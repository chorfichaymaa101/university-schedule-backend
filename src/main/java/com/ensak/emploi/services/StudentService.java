package com.ensak.emploi.services;

import com.ensak.emploi.model.Student;
import com.ensak.emploi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student findById(Long studentId) {
        return studentRepository.findById(studentId).orElse(null);
    }
}
