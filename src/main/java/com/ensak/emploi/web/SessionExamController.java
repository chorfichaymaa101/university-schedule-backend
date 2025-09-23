package com.ensak.emploi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ensak.emploi.model.Class;
import com.ensak.emploi.model.ModuleProgram;
import com.ensak.emploi.model.Module;
import com.ensak.emploi.model.Person;
import com.ensak.emploi.model.ProfProgram;
import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.SessionExam;
import com.ensak.emploi.repository.ClassRepository;
import com.ensak.emploi.repository.ModuleProgramRepository;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.PersonRepository;
import com.ensak.emploi.repository.ProfProgramRepository;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.services.SessionExamService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SessionExamController {

    @Autowired
    private SessionExamService sessionExamService;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ProfProgramRepository profProgramRepository;

    @Autowired
    ModuleProgramRepository moduleProgramRepository;

    @PostMapping("/sessionExam")
    public SessionExam createsessionExam(@RequestBody SessionExam sessionExam) {
        return sessionExamService.saveSessionExam(sessionExam);
    }

    @GetMapping("/sessionExam/{examCalendarId}")
    public List<SessionExam> getSessionExamByExamCalendar(@PathVariable("examCalendarId") Long examCalendarId) {

        List<SessionExam> sessionExams = sessionExamService.getSessionExamByExamCalendar(examCalendarId);
        return sessionExams;
    }

    @GetMapping("/profProgramById/{id}")
    public ProfProgram getProfProgramById(@PathVariable Long id) {
        return profProgramRepository.findProfProgramById(id);
    }

    ////////////
    @GetMapping("/getPrograms")
    public List<Program> getAllPrograms() {
        return programRepository.findAll();
    }

    @GetMapping("/getProfPrograms")
    public List<ProfProgram> getAllProfPrograms() {
        return profProgramRepository.findAll();
    }

    @GetMapping("/getModulePrograms")
    public List<ModuleProgram> getAllModulePrograms() {
        return moduleProgramRepository.findAll();
    }

//    @GetMapping("/profById/{id}")
//    public Optional<Person> getProfessorById(@PathVariable Long id){
//        return personRepository.findPersonById(id);
//    }
    @GetMapping("/getProfs")
    public List<Person> getAllProfs() {
        return personRepository.findAll();//A Revoir
    }

//    @GetMapping("/classById/{id}")
//    public Class getClassNameById(@PathVariable Long id) {
//        //return classRepository.findAll(); // Fetch all classes from the database
//        return classRepository.findClassById(id);
//    }
    @GetMapping("/getClasses")
    public List<Class> getAllClasses() {
        return classRepository.findAll();//A Revoir
    }

    @GetMapping("/getSessionExams")
    public List<SessionExam> getSessionExams(@RequestParam Long examCalendarId) {
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return sessionExamService.getSessionExamsByExamTableId(examCalendarId);
    }

    @GetMapping("/getSessionExam")
    public SessionExam getSessionExam(@RequestParam Long id, @RequestParam Long examCalendarId) {
        //get Time table by ID
        //Provide Time table (timeTableID - programID - Semestre ....) to Angular
        return sessionExamService.getSessionExamBySessionExamIdAndExamTableId(id, examCalendarId);
    }

    @PutMapping("/updateSessionExam")
    public void updateSession(@RequestBody SessionExam sessionExam) {
        try {
            sessionExamService.updateSessionExam(sessionExam);
        } catch (Exception e) {
            System.out.println("Exception thrown at updateSession method" + e);
        }
        //return session.getSession_id();
    }
}
