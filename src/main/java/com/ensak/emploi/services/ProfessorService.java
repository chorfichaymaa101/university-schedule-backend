package com.ensak.emploi.services;

import com.ensak.emploi.constents.EmploiConstants;
import com.ensak.emploi.model.*;
import com.ensak.emploi.repository.ProfProgramRepository;
import com.ensak.emploi.repository.ProgramRepository;
import com.ensak.emploi.repository.SessionRepository;
import com.ensak.emploi.repository.UserRepository;
import com.ensak.emploi.utils.EmploiUtils;
import com.ensak.emploi.wrapper.UserWrapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProfessorService implements IProfessorService {

    private UserRepository userRepository;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private ProfProgramRepository profProgramRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Transactional
    @Override
    public ResponseEntity<String> deleteProfessor(Long id) {
        try {
            profProgramRepository.deleteByProfessorId(id);
            sessionRepository.deleteByProfessorId(id);

            Optional optional = userRepository.findById(id);
            if (!optional.isEmpty()) {
                userRepository.deleteById(id);
                return EmploiUtils.getResponeEntity("User is deleted successfully", HttpStatus.OK);
            }
            return EmploiUtils.getResponeEntity("User id doesn't exist", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Professor getProfessorById(Long id) {
        return (Professor) userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Le prof avec l'id " + id + " n'existe pas!"));

    }

    /*
     * @Override
     * public Professor updateProfessor(Long id, Professor professor) {
     * professor.setId(id);
     * return (Professor) userRepository.save(professor);
     * }
     */
    @Override
    public ResponseEntity<?> updateProfessor(@PathVariable Long id, @RequestBody CreateProfessorRequest request) {
        try {
            // Vérifier si la liste des programmes est nulle ou vide
            if (request.getProgramNames() == null || request.getProgramNames().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Aucun programme sélectionné. Le professeur ne peut pas être sauvegardé.");
            }

            System.out.println(request.getProgramNames());

            List<Program> programs = programRepository.findAllByTitle(request.getProgramNames());

            System.out.println(programs);

            if (programs == null || programs.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Les programmes spécifiés n'existent pas dans la base de données.");
            }

            Professor professor = new Professor();
            professor.setName(request.getName());
            professor.setEmail(request.getEmail());
            professor.setId(id);
            System.out.println(request.getName());
            System.out.println(professor.getId());

            professor = userRepository.save(professor);
            System.out.println(professor.getId());

            profProgramRepository.deleteByProfessorId(id);

            for (Program program : programs) {
                ProfProgram profProgram = new ProfProgram();
                profProgram.setProfessor(professor);
                profProgram.setProgram(program);
                profProgramRepository.save(profProgram);
            }

            return ResponseEntity.ok("Le professeur et les programmes associés ont été sauvegardés avec succès.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<Person> getProfessors() {
        return userRepository.findUsersByRole("PROF");
    }

    @Override
    public Professor addProfessor(Professor professor) {
        return (Professor) userRepository.save(professor);
    }

    @Override
    public Page<Person> searchProfessors(String keyword, Pageable pageable) {
        return userRepository.searchWithPagination(keyword, "PROF", pageable);
    }

    public Page<Person> getProfss(Pageable pageable) {
        return userRepository.findUsersByRoleByPage("PROF", pageable);
    }

    @Override
    public List<Person> getAllProfessors() {
        return userRepository.findAllByRole("PROF");
    }

    @Override
    public List<Person> findProfessorByName(String name) {
        return userRepository.findByNom(name, "PROF");

    }

    @Override
    public Page<CreateProfessorRequest> getProfs(Pageable pageable) {
        try {

            Page<Professor> professorsPage = userRepository.findProfsByRoleByPage(pageable);
            System.out.println(professorsPage);

            // Map professors to CreateProfessorRequest with program names
            Page<CreateProfessorRequest> result = professorsPage.map(professor -> {
                System.out.println(professor.getId());

                // Collect the program names associated with the professor
                List<String> programNames = profProgramRepository.findProgramNamesByProfessorId(professor);
                System.out.println(programNames.getClass());
                // Create the request object with professor and program names
                CreateProfessorRequest request = new CreateProfessorRequest();

                request.setId(professor.getId());
                request.setName(professor.getName());
                request.setEmail(professor.getEmail());
                System.out.println(programNames);

                request.setProgramNames(programNames); // Set the program names

                System.out.println(request);

                return request;
            });

            return result;

        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException("Error occurred while fetching professors with programs", ex);
        }
    }

    @Override
    public ResponseEntity<?> createProfessorWithPrograms(@RequestBody CreateProfessorRequest request) {
        try {
            // Vérifier si la liste des programmes est nulle ou vide
            if (request.getProgramNames() == null || request.getProgramNames().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Aucun programme sélectionné. Le professeur ne peut pas être sauvegardé.");
            }

            System.out.println(request.getProgramNames());

            List<Program> programs = programRepository.findAllByTitle(request.getProgramNames());

            System.out.println(programs);

            if (programs == null || programs.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Les programmes spécifiés n'existent pas dans la base de données.");
            }

            Professor professor = new Professor();
            professor.setName(request.getName());
            professor.setEmail(request.getEmail());
            professor = userRepository.save(professor);

            System.out.println(professor.getId());

            for (Program program : programs) {
                ProfProgram profProgram = new ProfProgram();
                profProgram.setProfessor(professor);
                profProgram.setProgram(program);
                profProgramRepository.save(profProgram);
            }

            return ResponseEntity.ok("Le professeur et les programmes associés ont été sauvegardés avec succès.");
        } catch (Exception ex) {
            ex.printStackTrace();
            return EmploiUtils.getResponeEntity(EmploiConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Person findById(Long id) {
        return userRepository.getUerById(id, "PROF");
    }
}
