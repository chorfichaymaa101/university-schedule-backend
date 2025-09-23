package com.ensak.emploi.web;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.CreateProfessorRequest;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.Professor;
import com.ensak.emploi.services.IProfessorService;
import com.ensak.emploi.utils.EmploiUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", methods = { RequestMethod.GET, RequestMethod.POST,
        RequestMethod.PUT, RequestMethod.DELETE })
@Slf4j
@RestController
@RequestMapping("/professors")
@AllArgsConstructor
public class ProfessorController {

    private IProfessorService professorService;

    @GetMapping("/")
    public List<Person> getAllProfs() {
        return professorService.getProfessors();
    }

    @GetMapping
    public Page<CreateProfessorRequest> getAllProfsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return professorService.getProfs(pageable);
    }

    @GetMapping("/{id}")
    public Professor getProfessorById(@PathVariable Long id) {
        return professorService.getProfessorById(id);
    }

    /*
     * @PostMapping
     * public Professor createProfessor(@RequestBody Professor professor) {
     * return professorService.addProfessor(professor);
     * }
     */
    @PostMapping
    public ResponseEntity<?> createProfessorWithPrograms(@RequestBody CreateProfessorRequest request) {
        try {
            return professorService.createProfessorWithPrograms(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessor(@PathVariable Long id,
            @RequestBody CreateProfessorRequest updatedProfessor) {
        return professorService.updateProfessor(id, updatedProfessor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProfessor(@PathVariable Long id) {
        return professorService.deleteProfessor(id);
    }

    @GetMapping("/search")
    public Page<Person> searchProfessors(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return professorService.searchProfessors(keyword, pageable);
    }
}
