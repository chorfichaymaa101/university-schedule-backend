package com.ensak.emploi.web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.Class;
import com.ensak.emploi.model.enums.ClassType;
import com.ensak.emploi.services.ClassService;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:1000" })
public class ClassController {

    @Autowired
    private ClassService classservice;

    @GetMapping("/class/{sessiontype}/{capacity}")
    public List<Class> getClassByTypeCapacity(
            @PathVariable("sessiontype") Long sessiontype,
            @PathVariable("capacity") Long capacity) {

        List<Class> classes = new ArrayList<>();
        ClassType[] types = ClassType.values();

        if (sessiontype == 0 || sessiontype == 2) {
            classes.addAll(classservice.getClassByTypeCpacity(types[1], capacity));
            classes.addAll(classservice.getClassByTypeCpacity(types[2], capacity));
        } else if (sessiontype == 1) {
            classes = classservice.getClassByTypeCpacity(types[0], capacity);
        }
        return classes;
    }

    @GetMapping("/classById/{id}")
    public Class getClassById(@PathVariable("id") final Long id) {
        return classservice.getClassById(id);

    }

    @PostMapping("/classes")
    public Class createClass(@RequestBody Class classe) {
        return classservice.saveClass(classe);
    }

    @PutMapping("/classes/{id}")
    public Class updateClass(@PathVariable Long id, @RequestBody Class updatedClass) {
        return classservice.updateClass(id, updatedClass);
    }

    @DeleteMapping("/classes/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        return classservice.deleteClass(id);
    }

    @GetMapping("/classes")
    public ResponseEntity<List<Class>> getClasses() {
        List<Class> classes = classservice.findAllClasses(); // Service method to get all modules
        return ResponseEntity.ok(classes);
    }

     @GetMapping("/api/classes")
    public ResponseEntity<List<Class>> gettClasses() {
        List<Class> classes = classservice.findAllClasses();  // Service method to get all modules
        return ResponseEntity.ok(classes);
    }

}
