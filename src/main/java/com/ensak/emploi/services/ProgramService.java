package com.ensak.emploi.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.CreateModuleRequest;
import com.ensak.emploi.model.CreateProfessorRequest;
import com.ensak.emploi.model.ModuleProgram;
import com.ensak.emploi.model.Professor;
import com.ensak.emploi.model.Program;
import com.ensak.emploi.model.Module;
import com.ensak.emploi.model.enums.SemesterNumber;
import com.ensak.emploi.repository.ModuleProgramRepository;
import com.ensak.emploi.repository.ModuleRepository;
import com.ensak.emploi.repository.ProfProgramRepository;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.utils.EmploiUtils;

@Service
public class ProgramService {

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProfProgramRepository profProgramRepository;



    
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ModuleProgramRepository moduleProgramRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    public List<Program> findAllDistinctPrograms() {
    return programRepository.findDistinctPrograms();
}


    public List<Program> getProgramBySemester(final SemesterNumber semester) {
        return programRepository.existsBySemester(semester);
    }

    public Program getProgramById(final Long id) {
        return programRepository.findProgramById(id);
    }

    public List<Program> getAllPrograms() {
        return programRepository.findAllPrograms();
    }

    public List<CreateModuleRequest> getAllProgramss() {
        try {
            List<Program> programs = programRepository.findAllPrograms();
            System.out.println(programs);

            List<CreateModuleRequest> result = programs.stream().map(program -> {
                System.out.println(program.getId());

                List<String> programNames = moduleProgramRepository.findModuleNamesByProgramId(program);
                System.out.println(programNames);

                CreateModuleRequest request = new CreateModuleRequest();
                request.setId(program.getId());
                request.setprogramName(program.getProgramName());
                SemesterNumber s = program.getSemester();
                System.out.println(s);
                System.out.println(Integer.parseInt(s.name()));
                request.setSemester(Integer.parseInt(s.name()));
                request.setprogramName(program.getProgramName()); // Set the program names

                System.out.println(request);
                return request;
            }).collect(Collectors.toList());

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error occurred while fetching professors with programs", ex);
        }
    }

    public String getProgramNameById(Long programId) {
        Program programOptional = programRepository.findProgramById(programId);
        String programName = null;
        if (programOptional != null) {
            programName = programOptional.getProgramName();
        } else {
            System.out.println("Program with the id " + programId + " doesn't exist");
        }
        return programName;
    }

    public List<Program> findAllPrograms() {
        return programRepository.findAll();
    }

    public Optional<Program> findById(Long id) {
        return programRepository.findById(id);
    }
    /*
     * public String getProgramNameById(Long programId) {
     * return programRepository.findById(programId)
     * .map(Program::getProgramName)
     * .orElse("Unknown Program");
     * }
     */

    public Program saveProgram(Program program) {
        return programRepository.save(program);
    }

    public Program updateProgram(Long id, Program program) {
        program.setId(id);
        return programRepository.save(program);
    }

    public ResponseEntity<String> deleteProgram(Long id) {

        try {
            moduleProgramRepository.deleteByProgramId(id);
            Optional optional = programRepository.findById(id);
            if (!optional.isEmpty()) {
                programRepository.deleteById(id);
                return EmploiUtils.getResponeEntity("Program is deleted successfully", HttpStatus.OK);
            }
            return EmploiUtils.getResponeEntity("Program id doesn't exist", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> createProgramWithModules(@RequestBody CreateModuleRequest request) {
        try {
            // Vérifier si la liste des programmes est nulle ou vide
            if (request.getModuleNames() == null || request.getModuleNames().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Aucun module sélectionné. Le module ne peut pas être sauvegardé.");
            }

            System.out.println(request.toString());

            List<Module> modules = moduleRepository.findAllByTitle(request.getModuleNames());

            System.out.println(modules);

            if (modules == null || modules.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Les modules spécifiés n'existent pas dans la base de données.");
            }

            Program program = new Program();
            program.setProgramName(request.getprogramName());
            int semester = request.getSemester() + 1;
            program.setSemester(SemesterNumber.valueOf("S" + semester));
            program = programRepository.save(program);

            System.out.println(program.getId());

            for (Module module : modules) {
                ModuleProgram moduleProgram = new ModuleProgram();
                moduleProgram.setModuleId(module.getId());
                moduleProgram.setProgramId(program.getId());
                moduleProgramRepository.save(moduleProgram);
            }

            return ResponseEntity.ok("Le programe et les modules associés ont été sauvegardés avec succès.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
