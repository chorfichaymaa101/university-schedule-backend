package com.ensak.emploi.services;

import com.ensak.emploi.model.CreateStudent;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Student;
import com.ensak.emploi.wrapper.UserWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IUserService {

    ResponseEntity<String> signUp(CreateStudent student);

    ResponseEntity<List<Person>> getAllUser();

    Person addUser(Person Person);

    ResponseEntity<String> deleteUser(Long id);

    ResponseEntity<Person> getUserById(Long id, String role);

    Person updateUser(Long id, Person Person);

    List<Person> getUsers(String role);

    Page<Person> searchUsers(String keyword, Pageable pageable, String role);

    List<Person> getAllUsers(String role);

    List<Person> findUserByName(String name, String role);

    Person findByEmailId(String email);
}
