package com.ensak.emploi.web;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.CreateStudent;
import com.ensak.emploi.model.Student;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.services.IUserService;
import com.ensak.emploi.utils.EmploiUtils;
import com.ensak.emploi.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/students")
@AllArgsConstructor
public class StudentController {

    @Autowired
    private IUserService studentService;

    @GetMapping
    public List<Person> getAllStudents() {
        return studentService.getUsers("STUDENT");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getStudentById(@PathVariable Long id) {
        return studentService.getUserById(id, "STUDENT");
    }

    @PostMapping
    public ResponseEntity<String> createStudent(@RequestBody CreateStudent student) {
        try {
            return studentService.signUp(student);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping("/{id}")
    public Person updateUser(@PathVariable Long id, @RequestBody Student updatedStudent) {
        return studentService.updateUser(id, updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return studentService.deleteUser(id);
    }

    @GetMapping("/search")
    public Page<Person> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return studentService.searchUsers(keyword, pageable, "STUDENT");
    }
}
