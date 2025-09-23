package com.ensak.emploi.services;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Student;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.PersonRepository;
import com.ensak.emploi.repository.StudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<Person> getProfByProgram(final Long programId) {
        return personRepository.findProfByProgram(programId);
    }

    public Person getProfById(final Long id) {
        return personRepository.findProfByid(id);
    }

    public Optional<Person> findById(Long id) {
        return Optional.empty();
    }

    public String getProfessorNameById(Long profId) {
        return personRepository.findById(profId)
                .map(Person::getName)
                .orElse("Unknown Professor");
    }

    /*
    public Long getProgramIdByPersonId(Long personId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found with id: " + personId));
        return person.getProgramId();
    }*/

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public String getUserRoleById(Long userId) {
        Person person = personRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return person.getRole();
    }

    public Long getSemesterIdByPersonId(Long personId) {
        return personRepository.findById(personId)
                .map(Person::getSemesterId)
                .orElseThrow(() -> new IllegalArgumentException("Person not found"));
    }

    public String getProgramNameByPersonId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                            .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        return student.getName(); // Assuming the Program entity has a 'name' field
    }


}
