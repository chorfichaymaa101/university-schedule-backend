package com.ensak.emploi.web;

import com.ensak.emploi.model.Admin;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.services.IUserService;
import com.ensak.emploi.wrapper.UserWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/admins")
@AllArgsConstructor
public class AdminController {

    private IUserService adminService;

    @GetMapping
    public List<Person> getAllUsers() {

        return adminService.getUsers("ADMIN");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getUserById(@PathVariable Long id) {
        return adminService.getUserById(id, "ADMIN");
    }

    @PostMapping
    public Person createUser(@RequestBody Admin admin) {
        return adminService.addUser(admin);
    }

    @PutMapping("/{id}")
    public Person updateUser(@PathVariable Long id, @RequestBody Admin updatedAdmin) {
        return adminService.updateUser(id, updatedAdmin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }

    @GetMapping("/search")
    public Page<Person> searchUsers(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return adminService.searchUsers(keyword, pageable, "ADMIN");
    }
}
