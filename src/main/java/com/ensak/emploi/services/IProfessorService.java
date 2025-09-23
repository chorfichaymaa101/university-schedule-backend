package com.ensak.emploi.services;

import com.ensak.emploi.model.CreateProfessorRequest;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Professor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IProfessorService {

    Professor addProfessor(Professor professor);

    ResponseEntity<String> deleteProfessor(Long id);

    Page<CreateProfessorRequest> getProfs(Pageable pageable);

    Professor getProfessorById(Long id);

    public ResponseEntity<?> updateProfessor(Long id, CreateProfessorRequest request);

    List<Person> getProfessors();

    Page<Person> searchProfessors(String keyword, Pageable pageable);

    List<Person> getAllProfessors();

    List<Person> findProfessorByName(String name);

    ResponseEntity<?> createProfessorWithPrograms(CreateProfessorRequest request);
}
