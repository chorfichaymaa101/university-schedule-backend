package com.ensak.emploi.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.CreateModuleRequest;
import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.services.ProgramService;
import com.ensak.emploi.utils.EmploiUtils;

@RestController
@CrossOrigin(origins = { "http://localhost:4200", "http://10.0.2.2:1000" })
public class ProgramController {

    @Autowired
    private ProgramService programService;

    @GetMapping("/program/{semester}")
    public List<Program> getProgramBySemester(@PathVariable("semester") SemesterNumber semester) {
        List<Program> programs = programService.getProgramBySemester(semester);
        return programs;
    }

    @GetMapping("/programById/{id}")
    public Program getProgramById(@PathVariable("id") final Long id) {
        return programService.getProgramById(id);

    }

    @GetMapping("/programs")
    public List<Program> getAllPrograms() {
        List<Program> programs = programService.getAllPrograms();
        return programs;
    }

    @PostMapping("/programs")
    public ResponseEntity<?> createProfessorWithPrograms(@RequestBody CreateModuleRequest request) {
        try {
            return programService.createProgramWithModules(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @PutMapping("/programs/{id}")
    public Program updateProgram(@PathVariable Long id, @RequestBody Program updatedProgram) {
        return programService.updateProgram(id, updatedProgram);
    }

    @DeleteMapping("/programs/{id}")
    public ResponseEntity<String> deleteProgram(@PathVariable Long id) {
        return programService.deleteProgram(id);
    }

    @GetMapping("/programs/{id}")
    public Optional<Program> getProgramrById(@PathVariable Long id) {
        return programService.findById(id);
    }

    
    @GetMapping("/api/programs")
    public ResponseEntity<List<Program>> getPrograms() {
    List<Program> programs = programService.findAllDistinctPrograms();
    return ResponseEntity.ok(programs);
}

}
