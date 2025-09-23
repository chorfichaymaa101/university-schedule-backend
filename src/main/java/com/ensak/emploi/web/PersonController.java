package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.Person;
import com.ensak.emploi.services.PersonService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/prof/{programId}")
    public List<Person> getPersonByProgram(@PathVariable("programId") final Long programId) {
        return personService.getProfByProgram(programId);
    }

    @GetMapping("/profById/{id}")
    public Person getProfById(@PathVariable("id") final Long id) {
        return personService.getProfById(id);

    }
}
