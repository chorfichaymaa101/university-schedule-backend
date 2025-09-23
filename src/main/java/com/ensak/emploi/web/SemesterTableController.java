package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.SemesterTable;
import com.ensak.emploi.services.SemesterTableService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://10.0.2.2:1000"})
public class SemesterTableController {

    @Autowired
    private SemesterTableService semesterTableService;

    @PostMapping("/semestertable")
    public SemesterTable createsemestertable(@RequestBody SemesterTable semesterTable) {
        return semesterTableService.saveSemesterTable(semesterTable);
    }

    @GetMapping("/semestertable/{year}")
    public List<SemesterTable> getSemesterTableByYear(@PathVariable("year") String year) {

        List<SemesterTable> semesterTables = semesterTableService.getSemesterTableByYear(year);
        return semesterTables;
    }

    @GetMapping("/semestertable")
    public List<SemesterTable> getAllSemesterTables() {
        return semesterTableService.getAllSemesterTables();
    }

//    @GetMapping("/semestertable/{id}")
//    public SemesterTable getSemesterTableById(@PathVariable Long id) {
//        return semesterTableService.getSemesterTableById(id);
//    }
    @GetMapping("/getSemesterTable")
    public List<SemesterTable> getSemesterTable(@RequestParam String year) {
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return semesterTableService.getSemesterTable(year);
    }

    @GetMapping("/getSemesterTableById")
    public SemesterTable getSemesterTableById(@RequestParam Long semester_id) {
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return semesterTableService.getSemesterTableById(semester_id);
    }

    @PutMapping("/updateSemesterTable")
    public void updateSemesterTable(@RequestBody SemesterTable semesterTable) {
        try {
            semesterTableService.updateTimeTable(semesterTable);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method" + e);
        }
        //return semesterTable.getId();
    }

    @GetMapping("/api/semesters")
    public ResponseEntity<List<SemesterTable>> getSemesters() {
        List<SemesterTable> semesters = semesterTableService.findAllSemesters();  // Service method to get all modules
        return ResponseEntity.ok(semesters);
    }

}
