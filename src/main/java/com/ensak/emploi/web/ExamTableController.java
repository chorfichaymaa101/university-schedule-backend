package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ensak.emploi.model.ExamTable;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.services.ExamTableService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ExamTableController {

    @Autowired
    private ExamTableService examTableService;

    @PostMapping("/examtable")
    public ExamTable createtimeTable(@RequestBody ExamTable examTable) {
        return examTableService.savetimeTable(examTable);
    }

    @GetMapping("/examtable/{semester}/{academicYear}")
    public ExamTable getExamTableBySemseterYear(@PathVariable SemesterNumber semester, @PathVariable String academicYear) {
        return examTableService.getExamTableBySemseterYear(semester, academicYear);
    }

    @GetMapping("/countexamTable/{semester}/{academicYear}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable SemesterNumber semester, @PathVariable String academicYear) {
        boolean exists = examTableService.examExist(semester, academicYear);
        return ResponseEntity.ok(exists);
    }
    
    @GetMapping("/examtable")
    public Iterable<ExamTable> getAllExamTable() {
        return examTableService.getAllExamTable();
    }

    @GetMapping("/getExamTable")
    public ExamTable getExamTable(@RequestParam String academicYear, SemesterNumber semester){
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return examTableService.getExamTable(academicYear, semester);
    }

    @GetMapping("/getExamTableId")
    public Long getExamTableId(@RequestParam String academicYear, SemesterNumber semester){
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return examTableService.getExamTableId(academicYear, semester);
    }

    @PutMapping("/updateExamTable")
    public Long updateExamTable(@RequestBody ExamTable examTable){
        try{
            examTableService.updateExamTable(examTable);
        }catch (Exception e) {
            System.out.println("Exception thrown at updateTimeTable method" + e);
        }
        return examTable.getId();
    }


}
